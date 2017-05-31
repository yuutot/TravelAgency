package ua.travel.dao.builders;

/**
 * Created by yuuto on 5/24/17.
 */
public enum Condition {
    GREATER {
        @Override
        public String toString() {
            return ">";
        }
    }, LESS {
        @Override
        public String toString() {
            return "<";
        }
    }, EVEN {
        @Override
        public String toString() {
            return "=";
        }
    };

    public abstract String toString();
}