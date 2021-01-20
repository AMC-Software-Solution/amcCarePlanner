import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/consent/bloc/consent_bloc.dart';
import 'package:amcCarePlanner/entities/consent/consent_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'consent_route.dart';

class ConsentViewScreen extends StatelessWidget {
  ConsentViewScreen({Key key}) : super(key: ConsentRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesConsentViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ConsentBloc, ConsentState>(
              buildWhen: (previous, current) => previous.loadedConsent != current.loadedConsent,
              builder: (context, state) {
                return Visibility(
                  visible: state.consentStatusUI == ConsentStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    consentCard(state.loadedConsent, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ConsentRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget consentCard(Consent consent, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + consent.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Title : ' + consent.title.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + consent.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Give Consent : ' + consent.giveConsent.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Arrangements : ' + consent.arrangements.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service User Signature : ' + consent.serviceUserSignature.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Signature Image Url : ' + consent.signatureImageUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Consent Date : ' + (consent?.consentDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(consent.consentDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (consent?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(consent.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (consent?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(consent.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + consent.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + consent.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
