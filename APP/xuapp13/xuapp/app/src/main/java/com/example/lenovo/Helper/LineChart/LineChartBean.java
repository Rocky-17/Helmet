package com.example.lenovo.Helper.LineChart;

import java.util.List;

public class LineChartBean {

    private GRID0Bean GRID0;

    /**
     * 我的收益
     */
    public class IncomeBean {
        /**
         * tradeDate : 20180502
         * value : 0.03676598
         */
        private String tradeDate;
        private double value;

        public String getTradeDate() {
            return tradeDate;
        }

        public double getValue() {
            return value;
        }

    }

    /**
     * 沪深创指数
     */
    public class CompositeIndexBean {
        /**
         * rate : -0.00034196
         * tradeDate : 20180502
         */
        //private String rate;
        private String tradeDate;

        private double rate;
        public double getRate() {
            return rate;
        }

    }

    public GRID0Bean getGRID0() {
        return GRID0;
    }

    public void setGRID0(GRID0Bean GRID0) {
        this.GRID0 = GRID0;
    }

    public class GRID0Bean {
        private int code;
        private String message;
        private ResultBean result;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public class ResultBean {

            private List<CompositeIndexBean> compositeIndexGEM;
            private List<IncomeBean> clientAccumulativeRate;
            private List<CompositeIndexBean> compositeIndexShanghai;
            private List<CompositeIndexBean> compositeIndexShenzhen;

            public List<CompositeIndexBean> getCompositeIndexGEM() {
                return compositeIndexGEM;
            }

            public void setCompositeIndexGEM(List<CompositeIndexBean> compositeIndexGEM) {
                this.compositeIndexGEM = compositeIndexGEM;
            }

            public List<IncomeBean> getClientAccumulativeRate() {
                return clientAccumulativeRate;
            }

            public void setClientAccumulativeRate(List<IncomeBean> clientAccumulativeRate) {
                this.clientAccumulativeRate = clientAccumulativeRate;
            }

            public List<CompositeIndexBean> getCompositeIndexShanghai() {
                return compositeIndexShanghai;
            }

            public void setCompositeIndexShanghai(List<CompositeIndexBean> compositeIndexShanghai) {
                this.compositeIndexShanghai = compositeIndexShanghai;
            }

            public List<CompositeIndexBean> getCompositeIndexShenzhen() {
                return compositeIndexShenzhen;
            }

            public void setCompositeIndexShenzhen(List<CompositeIndexBean> compositeIndexShenzhen) {
                this.compositeIndexShenzhen = compositeIndexShenzhen;
            }
        }
    }

}
