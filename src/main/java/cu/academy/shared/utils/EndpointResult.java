package cu.academy.shared.utils;

public class EndpointResult<T> {
    public String error;
    public T result;

    public EndpointResult() {
    }

    public EndpointResult(T result, String error) {
        this.error = error;
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String toJsonString() {
        return "{" +
                "\"result\":" + (result != null ? ("\"" + result.toString() + "\"") : null) +
                ", \"error\":" + (error != null ? ("\"" + error + "\"") : null) +
                "}";
    }
}
