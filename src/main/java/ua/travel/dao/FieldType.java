package ua.travel.dao;

/**
 * Created by yuuto on 5/19/17.
 */
public enum  FieldType {
    INTEGER {
        @Override
        public String getTypeNameForLength(Integer... length) {
            return "int(" + length[0] + ")";
        }
    }, STRING {
        @Override
        public String getTypeNameForLength(Integer... length) {
            return "varchar(" + length[0] + ")";
        }
    }, OBJECT {
        @Override
        public String getTypeNameForLength(Integer... length) {
            return "int(" + length[0] + ")";
        }
    }, DATE{
        @Override
        public String getTypeNameForLength(Integer... length) {
            return "datetime";
        }
    }, DOUBLE{
        @Override
        public String getTypeNameForLength(Integer... length) {
            return "float(" + length[0] + ")";
        }
    }, ENUM{
        @Override
        public String getTypeNameForLength(Integer... length) {
            return "varchar(" + length[0] + ")";
        }
    };

    public abstract String getTypeNameForLength(Integer... length);
}
