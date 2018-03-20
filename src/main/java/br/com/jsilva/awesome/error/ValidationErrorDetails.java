package br.com.jsilva.awesome.error;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValidationErrorDetails extends ErrorDetails {

    private String field;
    private String fieldMessage;
    public static final class Builder {
        private String corporation;
        private String title;
        private int status;
        private String detail;
        private long timeStamp;
        private String developerMessage;
        private String field;
        private String fieldMessage;

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

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public ValidationErrorDetails build() {
            ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
            validationErrorDetails.setDetail(detail);
            validationErrorDetails.setCorporation(corporation);
            validationErrorDetails.setTitle(title);
            validationErrorDetails.setStatus(status);
            validationErrorDetails.setDeveloperMessage(developerMessage);
            validationErrorDetails.setTimeStamp(timeStamp);
            validationErrorDetails.setField(field);
            validationErrorDetails.setFieldMessage(fieldMessage);

            return validationErrorDetails;
        }
    }

}
