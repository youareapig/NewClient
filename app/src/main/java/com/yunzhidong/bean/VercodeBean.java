package com.yunzhidong.bean;

/**
 * Created by Dell on 2017/3/8.
 */
public class VercodeBean {

    /**
     * status : 1
     * msg : 验证成功
     * data : {"identify":"MDAwMDAwMDAwMISHst-Dh6LctIabZrHefauvn36RgJlybw"}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * identify : MDAwMDAwMDAwMISHst-Dh6LctIabZrHefauvn36RgJlybw
         */

        private String identify;

        public String getIdentify() {
            return identify;
        }

        public void setIdentify(String identify) {
            this.identify = identify;
        }
    }
}
