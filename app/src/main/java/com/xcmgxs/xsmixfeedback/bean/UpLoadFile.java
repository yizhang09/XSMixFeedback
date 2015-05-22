package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by zhangyi on 2015-05-21.
 */
@SuppressWarnings("serial")
public class UpLoadFile implements Serializable {

    @JsonProperty("Success")
    private boolean _success;

    @JsonProperty("Msg")
    private String _msg;

    @JsonProperty("File")
    private XSFile _file;

    public boolean isSuccess() {
        return _success;
    }

    public void setSuccess(boolean success) {
        this._success = success;
    }

    public String getMsg() {
        return _msg;
    }

    public void setMsg(String msg) {
        this._msg = msg;
    }

    public XSFile getFile() {
        return _file;
    }

    public void setFile(XSFile file) {
        this._file = file;
    }

    class XSFile {
        @JsonProperty("filename")
        private String _filename;

        @JsonProperty("title")
        private String _title;

        @JsonProperty("url")
        private String _url;

        public String getFilename() {
            return _filename;
        }

        public void setFilename(String filename) {
            this._filename = filename;
        }

        public String getTitle() {
            return _title;
        }

        public void setTitle(String title) {
            this._title = title;
        }

        public String getUrl() {
            return _url;
        }

        public void setUrl(String url) {
            this._url = url;
        }
    }
}
