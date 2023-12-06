package cw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Result {
    // https://www.codewars.com/kata/59c5d0b0a25c8c99ca000237/train/java
    public static boolean line(final char[][] grid) {
        return isLineValidFromStartPoint(grid, findStartPoint(grid)) || isLineValidFromStartPoint(grid, findAnotherStartPoint(grid));
    }

    private static boolean isLineValidFromStartPoint(final char[][] grid, Point startPoint) {
        List<Point> lineOfAlreadyExistedPoints;
        boolean possibleRoute = false;
        lineOfAlreadyExistedPoints = new ArrayList<>();
        lineOfAlreadyExistedPoints.add(startPoint);
        Point previous = null;
        Point current = startPoint;
        Point next, cache;

        while (isValidStep(grid, previous, current, lineOfAlreadyExistedPoints)) {
            next = findNextPoint(grid, previous, current, lineOfAlreadyExistedPoints);
            lineOfAlreadyExistedPoints.add(next);
            if (next.getX() == 999) continue;
            if (charInPointByLocation(grid, next) == 'X') {
                if (isExtraSignsAreOnTheField(grid, lineOfAlreadyExistedPoints))
                    return false;
                possibleRoute = true;
                break;
            }
            cache = current;
            current = next;
            previous = cache;
        }

        return possibleRoute;
    }

    private static boolean isExtraSignsAreOnTheField(final char[][] grid, List<Point> lineOfAlreadyExistedPoints) {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] != ' ' && !lineOfAlreadyExistedPoints.contains(new Point(j, i)))
                    return true;
        return false;
    }

    private static Point findNextPoint(final char[][] grid, Point previous, Point current, List<Point> lineOfAlreadyExistedPoints) {
        char currentChar = charInPointByLocation(grid, current);
        char nextChar;
        Point next;

        if (currentChar == 'X' && previous == null) {
            //up
            next = new Point((int) current.getX(), (int) current.getY() - 1);
            if (next.getY() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return next;
            }
            //right
            next = new Point((int) current.getX() + 1, (int) current.getY());
            if (next.getX() < grid[0].length) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return next;
            }
            //down
            next = new Point((int) current.getX(), (int) current.getY() + 1);
            if (next.getY() < grid.length) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return next;
            }
            //left
            next = new Point((int) current.getX() - 1, (int) current.getY());
            if (next.getX() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return next;
            }

        } else if (currentChar == '-') {
            //right
            next = new Point((int) current.getX() + 1, (int) current.getY());
            if (next.getX() < grid[0].length) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return next;
            }
            //left
            next = new Point((int) current.getX() - 1, (int) current.getY());
            if (next.getX() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return next;
            }

        } else if (currentChar == '|') {
            //up
            next = new Point((int) current.getX(), (int) current.getY() - 1);
            if (next.getY() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return next;
            }
            //down
            next = new Point((int) current.getX(), (int) current.getY() + 1);
            if (next.getY() < grid.length) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return next;
            }

        } else if (currentChar == '+') {
            double random = Math.random();
            //up
            if ((random > 0.00 && random <= 0.25) && previous.getY() != current.getY() + 1) {
                next = new Point((int) current.getX(), (int) current.getY() - 1);
                if (next.getY() >= 0) {
                    nextChar = charInPointByLocation(grid, next);
                    if (!lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return next;
                }
            }
            //right
            if ((random > 0.25 && random <= 0.50) && previous.getX() != current.getX() - 1) {
                next = new Point((int) current.getX() + 1, (int) current.getY());
                if (next.getX() < grid[0].length) {
                    nextChar = charInPointByLocation(grid, next);
                    if (!lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return next;
                }
            }
            //down
            if ((random > 0.50 && random <= 0.75) && previous.getY() != current.getY() - 1) {
                next = new Point((int) current.getX(), (int) current.getY() + 1);
                if (next.getY() < grid.length) {
                    nextChar = charInPointByLocation(grid, next);
                    if (!lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return next;
                }
            }
            //left
            if (previous.getX() != current.getX() + 1) {
                next = new Point((int) current.getX() - 1, (int) current.getY());
                if (next.getX() >= 0) {
                    nextChar = charInPointByLocation(grid, next);
                    if (!lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return next;
                }
            }

        } else System.out.println("Error message from findNextPoint: unknown char");

        return new Point(999, 999);
    }

    private static char charInPointByLocation(final char[][] grid, Point p) {
        return grid[(int) p.getY()][(int) p.getX()];
    }

    private static Point findStartPoint(final char[][] grid) {
        Point startPoint = new Point();
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] == 'X') {
                    startPoint.setLocation(j, i);
                    return startPoint;
                }
        return startPoint;
    }

    private static Point findAnotherStartPoint(final char[][] grid) {
        Point startPoint = new Point();
        for (int i = grid.length - 1; i >= 0; i--)
            for (int j = grid[0].length - 1; j >= 0; j--)
                if (grid[i][j] == 'X') {
                    startPoint.setLocation(j, i);
                    return startPoint;
                }
        return startPoint;
    }

    private static boolean isValidStep(final char[][] grid, Point previous, Point current, List<Point> lineOfAlreadyExistedPoints) {
        char currentChar = charInPointByLocation(grid, current);
        Point next;
        char nextChar, nextCharL, nextCharR;
        int howManyNextPoints = 0;

        if (currentChar == 'X' && previous == null) {
            //up
            next = new Point((int) current.getX(), (int) current.getY() - 1);
            if (next.getY() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|')) howManyNextPoints++;
            }
            //right
            next = new Point((int) current.getX() + 1, (int) current.getY());
            if (next.getX() < grid[0].length) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-')) howManyNextPoints++;
            }
            //down
            next = new Point((int) current.getX(), (int) current.getY() + 1);
            if (next.getY() < grid.length) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|')) howManyNextPoints++;
            }
            //left
            next = new Point((int) current.getX() - 1, (int) current.getY());
            if (next.getX() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-')) howManyNextPoints++;
            }

            if (howManyNextPoints == 0) return false;

            return howManyNextPoints <= 1;

        } else if (currentChar == '-') {
            //right
            next = new Point((int) current.getX() + 1, (int) current.getY());
            if (next.getX() < grid[0].length) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return true;
            }
            //left
            next = new Point((int) current.getX() - 1, (int) current.getY());
            if (next.getX() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                return !lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '-');
            }

        } else if (currentChar == '|') {
            //up
            next = new Point((int) current.getX(), (int) current.getY() - 1);
            if (next.getY() >= 0) {
                nextChar = charInPointByLocation(grid, next);
                if (!lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return true;
            }
            //down
            next = new Point((int) current.getX(), (int) current.getY() + 1);
            if (next.getY() < grid.length) {
                nextChar = charInPointByLocation(grid, next);
                return !lineOfAlreadyExistedPoints.contains(next) &&
                        (nextChar == 'X' || nextChar == '+' || nextChar == '|');
            }

        } else if (currentChar == '+') {
            //ambiguity check---------------------------------------------------------------------
            //up (check left and right and if they are legal return false)
            if (previous.getY() == current.getY() + 1) {
                Point nextL = new Point((int) current.getX() - 1, (int) current.getY());
                Point nextR = new Point((int) current.getX() + 1, (int) current.getY());
                if (nextL.getX() >= 0 && nextR.getX() < grid[0].length) {
                    nextCharL = charInPointByLocation(grid, nextL);
                    nextCharR = charInPointByLocation(grid, nextR);
                    if (!lineOfAlreadyExistedPoints.contains(nextL) && !lineOfAlreadyExistedPoints.contains(nextR) &&
                            (nextCharL == 'X' || nextCharL == '+' || nextCharL == '-') &&
                            (nextCharR == 'X' || nextCharR == '+' || nextCharR == '-')) return false;
                }
            }

            //down (check left and right and if they are legal return false)
            if (previous.getY() == current.getY() - 1) {
                Point nextL = new Point((int) current.getX() - 1, (int) current.getY());
                Point nextR = new Point((int) current.getX() + 1, (int) current.getY());
                if (nextL.getX() >= 0 && nextR.getX() < grid[0].length) {
                    nextCharL = charInPointByLocation(grid, nextL);
                    nextCharR = charInPointByLocation(grid, nextR);
                    if (!lineOfAlreadyExistedPoints.contains(nextL) && !lineOfAlreadyExistedPoints.contains(nextR) &&
                            (nextCharL == 'X' || nextCharL == '+' || nextCharL == '-') &&
                            (nextCharR == 'X' || nextCharR == '+' || nextCharR == '-')) return false;
                }
            }

            //left (check up and down and if they are legal return false)
            if (previous.getX() == current.getX() + 1) {
                Point nextL = new Point((int) current.getX(), (int) current.getY() + 1);
                Point nextR = new Point((int) current.getX(), (int) current.getY() - 1);
                if (nextR.getY() >= 0 && nextL.getY() < grid.length) {
                    nextCharL = charInPointByLocation(grid, nextL);
                    nextCharR = charInPointByLocation(grid, nextR);
                    if (!lineOfAlreadyExistedPoints.contains(nextL) && !lineOfAlreadyExistedPoints.contains(nextR) &&
                            (nextCharL == 'X' || nextCharL == '+' || nextCharL == '|') &&
                            (nextCharR == 'X' || nextCharR == '+' || nextCharR == '|')) return false;
                }
            }

            //right (check up and down and if they are legal return false)
            if (previous.getX() == current.getX() - 1) {
                Point nextL = new Point((int) current.getX(), (int) current.getY() - 1);
                Point nextR = new Point((int) current.getX(), (int) current.getY() + 1);
                if (nextL.getY() >= 0 && nextR.getY() < grid.length) {
                    nextCharL = charInPointByLocation(grid, nextL);
                    nextCharR = charInPointByLocation(grid, nextR);
                    if (!lineOfAlreadyExistedPoints.contains(nextL) && !lineOfAlreadyExistedPoints.contains(nextR) &&
                            (nextCharL == 'X' || nextCharL == '+' || nextCharL == '|') &&
                            (nextCharR == 'X' || nextCharR == '+' || nextCharR == '|')) return false;
                }
            }
            //------------------------------------------------------------------------------------
            //up
            if (previous.getY() != current.getY() + 1) {
                next = new Point((int) current.getX(), (int) current.getY() - 1);
                if (next.getY() >= 0 && previous.getY() != current.getY() + 1) {
                    nextChar = charInPointByLocation(grid, next);
                    if (!lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return true;
                }
            }
            //right
            if (previous.getX() != current.getX() - 1) {
                next = new Point((int) current.getX() + 1, (int) current.getY());
                if (next.getX() < grid[0].length && previous.getX() != current.getX() - 1) {
                    nextChar = charInPointByLocation(grid, next);
                    if (!lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '-')) return true;
                }
            }
            //down
            if (previous.getY() != current.getY() - 1) {
                next = new Point((int) current.getX(), (int) current.getY() + 1);
                if (next.getY() < grid.length && previous.getY() != current.getY() - 1) {
                    nextChar = charInPointByLocation(grid, next);
                    if (!lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '|')) return true;
                }
            }
            //left
            if (previous.getX() != current.getX() + 1) {
                next = new Point((int) current.getX() - 1, (int) current.getY());
                if (next.getX() >= 0 && previous.getX() != current.getX() + 1) {
                    nextChar = charInPointByLocation(grid, next);
                    return next.getX() > 0 && !lineOfAlreadyExistedPoints.contains(next) &&
                            (nextChar == 'X' || nextChar == '+' || nextChar == '-');
                }
            }

        } else
            System.out.println("from isValidStep: Error message, unknown char: " + currentChar);

        return false;
    }

    private static char[][] makeGrid(String[] ss) {
        char[][] grid = new char[ss.length][ss[0].length()];
        for (int i = 0; i < ss.length; i++)
            for (int j = 0; j < ss[0].length(); j++)
                grid[i][j] = ss[i].charAt(j);
        return grid;
    }

    public static void main(String[] args) {
        final char[][] grid1 = makeGrid(new String[]{ //4x22
                "                      ",
                "   +-------+          ",
                "   |      +++---+     ",
                "X--+      +-+   X     "
        });
        System.out.println(line(grid1));
    }
}
