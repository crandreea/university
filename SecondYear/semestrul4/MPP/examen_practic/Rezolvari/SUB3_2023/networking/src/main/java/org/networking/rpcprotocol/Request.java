package org.networking.rpcprotocol;

import java.io.Serializable;

public class Request implements Serializable {
    private RequestType type;
    private Object data;

    Request() {
    }

    public RequestType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }

    public static class Builder {
        private final Request request = new Request();

        public Builder type(RequestType type) {
            request.type(type);
            return this;
        }

        public Builder data(Object data) {
            request.data(data);
            return this;
        }

        public Request build() {
            return request;
        }
    }

    private void data(Object data) {
        this.data = data;
    }

    private void type(RequestType type) {
        this.type = type;
    }

}