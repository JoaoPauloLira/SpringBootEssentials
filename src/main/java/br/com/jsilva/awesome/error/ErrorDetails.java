package br.com.jsilva.awesome.error;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorDetails {
    private String corporation;
    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String developerMessage;


    public static final class Builder {
        private String corporation;
        private String title;
        private int status;
        private String detail;
        private long timeStamp;
        private String developerMessage;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder corporation(String corporation) {
            this.corporation = corporation;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorDetails build() {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setCorporation(corporation);
            errorDetails.setTitle(title);
            errorDetails.setStatus(status);
            errorDetails.setDetail(detail);
            errorDetails.setTimeStamp(timeStamp);
            errorDetails.setDeveloperMessage(developerMessage);
            return errorDetails;
        }
    }
}
