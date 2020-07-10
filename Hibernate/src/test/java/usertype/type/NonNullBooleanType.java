package usertype.type;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.PrimitiveType;
import org.hibernate.type.descriptor.java.BooleanTypeDescriptor;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class NonNullBooleanType extends AbstractSingleColumnStandardBasicType<Boolean>
        implements PrimitiveType<Boolean>, DiscriminatorType<Boolean> {

    public static final NonNullBooleanType INSTANCE = new NonNullBooleanType();

    public NonNullBooleanType() {
        super(org.hibernate.type.descriptor.sql.BooleanTypeDescriptor.INSTANCE, NonNullBooleanTypeDescriptor.INSTANCE);
    }

    public NonNullBooleanType(SqlTypeDescriptor sqlTypeDescriptor, JavaTypeDescriptor<Boolean> javaTypeDescriptor) {
        super(sqlTypeDescriptor, NonNullBooleanTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "boolean_non_null";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return false;
    }

    @Override
    public String objectToSQLString(Boolean value, Dialect dialect) {
        return BooleanTypeDescriptor.INSTANCE.toString(value);
    }

    @Override
    public Boolean stringToObject(String string) {
        return BooleanTypeDescriptor.INSTANCE.fromString(string);
    }

    @Override
    public Class getPrimitiveClass() {
        return boolean.class;
    }

    @Override
    public Serializable getDefaultValue() {
        return Boolean.FALSE;
    }
}
