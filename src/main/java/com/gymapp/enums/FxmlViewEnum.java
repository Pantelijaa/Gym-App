package com.gymapp.enums;

public enum FxmlViewEnum {
    
    DBSELECTOR {
        @Override
        public String getFxmlFile() {
            return "views/dbSelector.fxml";
        }

        public int getSidePanelIndex() {
            return -1;
        }
    }, DASHBOARD {
        @Override
        public String getFxmlFile() {
            return "views/dashboard.fxml";
        }

        @Override
        public int getSidePanelIndex() {
            return 0;
        }
    }, LIST {
        @Override
        public String getFxmlFile() {
            return "views/list.fxml";
        }
        
        @Override
        public int getSidePanelIndex() {
            return 1;
        }
    }, ADD {
        @Override
        public String getFxmlFile() {
            return "views/add.fxml";
        }

        @Override
        public int getSidePanelIndex() {
            return 2;
        }
    }, SCAN {
        @Override
        public String getFxmlFile() {
            return "views/scan.fxml";
        }

        @Override
        public int getSidePanelIndex() {
            return 4;
        }
    }, SCANVIEWER {
        @Override
        public String getFxmlFile() {
            return "views/scanViewer.fxml";
        }

        @Override
        public int getSidePanelIndex() {
            return 4;
        }
    }, MEMBERSHIP {
        @Override
        public String getFxmlFile() {
            return "views/membership.fxml";
        }

        @Override
        public int getSidePanelIndex() {
            return 3;
        }
    };

    abstract public int getSidePanelIndex();
    abstract public String getFxmlFile();

}
