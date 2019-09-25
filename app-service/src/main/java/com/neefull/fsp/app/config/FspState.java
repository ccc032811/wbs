package com.neefull.fsp.app.config;

public class FspState {

    /**
     * 项目团队状态：0 初始 1生效 -1 删除
     */
    public enum PROJECT_TEAM_STATE {
        Effective(1), DisEffective(0), delete(-1);

        int code;

        PROJECT_TEAM_STATE(int code) {
            this.code = code;
        }

        public int getState() {

            return code;
        }
    }

    /**
     * 项目团队状态：0 初始 1生效 -1 删除
     */
    public enum USER_TYPE {
        CORP(0), FREELENCER(1);

        int code;

        USER_TYPE(int code) {
            this.code = code;
        }

        public int TYPE() {

            return code;
        }
    }

}
