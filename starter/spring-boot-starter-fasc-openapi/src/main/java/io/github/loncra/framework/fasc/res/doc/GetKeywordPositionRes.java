package io.github.loncra.framework.fasc.res.doc;

import java.util.List;

public class GetKeywordPositionRes {
    private String keyword;
    private List<Position> positions;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public static class Position {
        private int positionPageNo;
        private Coordinate coordinate;

        public int getPositionPageNo() {
            return positionPageNo;
        }

        public void setPositionPageNo(int positionPageNo) {
            this.positionPageNo = positionPageNo;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        public static class Coordinate {
            private String positionX;
            private String positionY;

            public String getPositionX() {
                return positionX;
            }

            public void setPositionX(String positionX) {
                this.positionX = positionX;
            }

            public String getPositionY() {
                return positionY;
            }

            public void setPositionY(String positionY) {
                this.positionY = positionY;
            }
        }


    }
}
