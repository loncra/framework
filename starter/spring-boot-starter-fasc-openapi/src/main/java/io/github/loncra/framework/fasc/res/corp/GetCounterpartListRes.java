package io.github.loncra.framework.fasc.res.corp;

import java.util.List;

public class GetCounterpartListRes {

    private List<CounterpartInfo> counterpartInfos;


    public static class CounterpartInfo {
        private String corpName;
        private String corpIdentNo;
        private String status;

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public String getCorpIdentNo() {
            return corpIdentNo;
        }

        public void setCorpIdentNo(String corpIdentNo) {
            this.corpIdentNo = corpIdentNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    public List<CounterpartInfo> getCounterpartInfos() {
        return counterpartInfos;
    }

    public void setCounterpartInfos(List<CounterpartInfo> counterpartInfos) {
        this.counterpartInfos = counterpartInfos;
    }
}