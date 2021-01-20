import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/currency/bloc/currency_bloc.dart';
import 'package:amcCarePlanner/entities/currency/currency_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'currency_route.dart';

class CurrencyViewScreen extends StatelessWidget {
  CurrencyViewScreen({Key key}) : super(key: CurrencyRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesCurrencyViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<CurrencyBloc, CurrencyState>(
              buildWhen: (previous, current) => previous.loadedCurrency != current.loadedCurrency,
              builder: (context, state) {
                return Visibility(
                  visible: state.currencyStatusUI == CurrencyStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    currencyCard(state.loadedCurrency, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, CurrencyRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget currencyCard(Currency currency, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + currency.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Currency : ' + currency.currency.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Currency Iso Code : ' + currency.currencyIsoCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Currency Symbol : ' + currency.currencySymbol.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Currency Logo Url : ' + currency.currencyLogoUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + currency.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
