package de.fhws.biedermann.webshop.api.states;

import de.fhws.biedermann.webshop.models.ArticleVersion;
import de.fhws.biedermann.webshop.models.Order;
import de.fhws.biedermann.webshop.models.User;
import de.fhws.biedermann.webshop.utils.handler.FlawHandler;

import java.util.List;

public class OrderState extends AbstractState
{

	private OrderState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	public static void checkPrice( Order order, String uuid)
	{
		List<ArticleVersion> articles = order.getSpecifiedItems();
		if (computePrice(articles) != order.getAmount()) {
			FlawHandler.priceOrder(uuid);
		}
	}

	private static double computePrice( List<ArticleVersion> articles)
	{
		double sum = 0;
		for (ArticleVersion article : articles)
		{
			sum += article.getAmount() * article.getQuantity();
		}
		return sum;
	}

	@Override void lookForFlaw( )
	{
		if ( this.modelToWorkWith instanceof Order order ){
			checkPrice( order, this.uuid );
		}
	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new OrderState( this );
		}
	}
}
