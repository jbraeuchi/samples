package usertype.type;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.LiteralType;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

// Converts null to Boolean.FALSE
public class NonNullBooleanCustomType implements EnhancedUserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.BOOLEAN};
    }

    @Override
    public Class returnedClass() {
        return Boolean.class;
    }

    @Override
    public boolean equals(Object o1, Object o2) throws HibernateException {
        return Objects.equals(o1, o2);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        Object value = resultSet.getObject(names[0]);
        if (value == null) {
            return Boolean.FALSE;
        } else {
            return resultSet.getBoolean(names[0]);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value != null) {
            preparedStatement.setBoolean(index, (Boolean) value);
        } else {
            preparedStatement.setBoolean(index, Boolean.FALSE);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return o instanceof Boolean ? (Boolean) o : Boolean.FALSE;
    }

    @Override
    public Object assemble(Serializable serializable, Object owner) throws HibernateException {
        return serializable instanceof Boolean ? (Boolean) serializable : Boolean.FALSE;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @Override
    public String objectToSQLString(Object value) {
        return value != null ? value.toString() : "null";
    }

    @Override
    public String toXMLString(Object value) {
        return null;
    }

    @Override
    public Object fromXMLString(String xmlValue) {
        return null;
    }


}
