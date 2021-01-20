import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/currency/bloc/currency_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/currency/currency_model.dart';
import 'currency_route.dart';

class CurrencyUpdateScreen extends StatelessWidget {
  CurrencyUpdateScreen({Key key}) : super(key: CurrencyRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<CurrencyBloc, CurrencyState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, CurrencyRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<CurrencyBloc, CurrencyState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesCurrencyUpdateTitle:
S.of(context).pageEntitiesCurrencyCreateTitle;
                 return Text(title);
                }
            ),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: Column(children: <Widget>[settingsForm(context)]),
          ),
      ),
    );
  }

  Widget settingsForm(BuildContext context) {
    return Form(
      child: Wrap(runSpacing: 15, children: <Widget>[
          currencyField(),
          currencyIsoCodeField(),
          currencySymbolField(),
          currencyLogoUrlField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget currencyField() {
        return BlocBuilder<CurrencyBloc, CurrencyState>(
            buildWhen: (previous, current) => previous.currency != current.currency,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CurrencyBloc>().currencyController,
                  onChanged: (value) { context.bloc<CurrencyBloc>()
                    .add(CurrencyChanged(currency:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCurrencyCurrencyField));
            }
        );
      }
      Widget currencyIsoCodeField() {
        return BlocBuilder<CurrencyBloc, CurrencyState>(
            buildWhen: (previous, current) => previous.currencyIsoCode != current.currencyIsoCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CurrencyBloc>().currencyIsoCodeController,
                  onChanged: (value) { context.bloc<CurrencyBloc>()
                    .add(CurrencyIsoCodeChanged(currencyIsoCode:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCurrencyCurrencyIsoCodeField));
            }
        );
      }
      Widget currencySymbolField() {
        return BlocBuilder<CurrencyBloc, CurrencyState>(
            buildWhen: (previous, current) => previous.currencySymbol != current.currencySymbol,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CurrencyBloc>().currencySymbolController,
                  onChanged: (value) { context.bloc<CurrencyBloc>()
                    .add(CurrencySymbolChanged(currencySymbol:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCurrencyCurrencySymbolField));
            }
        );
      }
      Widget currencyLogoUrlField() {
        return BlocBuilder<CurrencyBloc, CurrencyState>(
            buildWhen: (previous, current) => previous.currencyLogoUrl != current.currencyLogoUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CurrencyBloc>().currencyLogoUrlController,
                  onChanged: (value) { context.bloc<CurrencyBloc>()
                    .add(CurrencyLogoUrlChanged(currencyLogoUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCurrencyCurrencyLogoUrlField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<CurrencyBloc,CurrencyState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesCurrencyHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<CurrencyBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<CurrencyBloc, CurrencyState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(CurrencyState state, BuildContext context) {
    String notificationTranslated = '';
    MaterialColor notificationColors;

    if (state.generalNotificationKey.toString().compareTo(HttpUtils.errorServerKey) == 0) {
      notificationTranslated =S.of(context).genericErrorServer;
      notificationColors = Theme.of(context).errorColor;
    } else if (state.generalNotificationKey.toString().compareTo(HttpUtils.badRequestServerKey) == 0) {
      notificationTranslated =S.of(context).genericErrorBadRequest;
      notificationColors = Theme.of(context).errorColor;
    }

    return Text(
      notificationTranslated,
      textAlign: TextAlign.center,
      style: TextStyle(fontSize: Theme.of(context).textTheme.bodyText1.fontSize,
          color: notificationColors),
    );
  }

  submit(BuildContext context) {
    return BlocBuilder<CurrencyBloc, CurrencyState>(
        buildWhen: (previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          String buttonLabel = state.editMode == true ?
S.of(context).entityActionEdit.toUpperCase():
S.of(context).entityActionCreate.toUpperCase();
          return RaisedButton(
            child: Container(
                width: MediaQuery.of(context).size.width,
                child: Center(
                  child: Visibility(
                    replacement: CircularProgressIndicator(value: null),
                    visible: !state.formStatus.isSubmissionInProgress,
                    child: Text(buttonLabel),
                  ),
                )),
            onPressed: state.formStatus.isValidated ? () => context.bloc<CurrencyBloc>().add(CurrencyFormSubmitted()) : null,
          );
        }
    );
  }
}
