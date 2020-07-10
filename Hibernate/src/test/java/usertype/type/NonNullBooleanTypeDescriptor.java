package usertype.type;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.BooleanTypeDescriptor;

public class NonNullBooleanTypeDescriptor extends BooleanTypeDescriptor {
    public static final NonNullBooleanTypeDescriptor INSTANCE = new NonNullBooleanTypeDescriptor();

    @Override
    public <X> Boolean wrap(X value, WrapperOptions options) {
        if (value != null) {
            return super.wrap(value, options);
        } else {
            return super.wrap(Boolean.FALSE, options);
        }
    }

    @Override
    public <X> X unwrap(Boolean value, Class<X> type, WrapperOptions options) {
        if (value != null) {
            return super.unwrap(value, type, options);
        } else {
            return super.unwrap(Boolean.FALSE, type, options);
        }
    }

}
