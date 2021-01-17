
import ch.brj.jaxb.xsd.sample2.Ingredients;
import ch.brj.jaxb.xsd.sample2.ObjectFactory;

import ch.brj.jaxb.xsd.sample2.Recipe;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class TestSample2 {

    @Test
    void test() throws JAXBException {
        ObjectFactory of = new ObjectFactory();

        Ingredients ingredients = of.createIngredients();
        ingredients.getItem().add("Ingredient 1");
        ingredients.getItem().add("Ingredient 2");

        Recipe recipe = of.createRecipe();
        recipe.setRecipeName("The Name");
        recipe.setMeal("The Meal");
        recipe.setIngredients(ingredients);

        JAXBContext jaxbContext = JAXBContext.newInstance(Recipe.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(recipe, writer);

        System.out.println(writer.toString());
    }
}
