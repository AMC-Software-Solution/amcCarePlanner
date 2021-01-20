import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/country/bloc/country_bloc.dart';
import 'package:amcCarePlanner/entities/country/country_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'country_route.dart';

class CountryViewScreen extends StatelessWidget {
  CountryViewScreen({Key key}) : super(key: CountryRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesCountryViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<CountryBloc, CountryState>(
              buildWhen: (previous, current) => previous.loadedCountry != current.loadedCountry,
              builder: (context, state) {
                return Visibility(
                  visible: state.countryStatusUI == CountryStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    countryCard(state.loadedCountry, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, CountryRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget countryCard(Country country, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + country.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Country Name : ' + country.countryName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Country Iso Code : ' + country.countryIsoCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Country Flag Url : ' + country.countryFlagUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Country Calling Code : ' + country.countryCallingCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Country Tel Digit Length : ' + country.countryTelDigitLength.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (country?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(country.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (country?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(country.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + country.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
