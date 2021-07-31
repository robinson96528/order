package com.project.order.model.response;

import java.util.Date;

public class ExceptionResponse {
        private String message;
        private Date dateTime;

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public Date getDateTime() {
            return dateTime;
        }
        public void setDateTime(Date dateTime) {
            this.dateTime = dateTime;
        }
}
