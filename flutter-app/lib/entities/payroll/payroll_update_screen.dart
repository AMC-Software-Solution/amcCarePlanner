import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/payroll/bloc/payroll_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/payroll/payroll_model.dart';
import 'payroll_route.dart';

class PayrollUpdateScreen extends StatelessWidget {
  PayrollUpdateScreen({Key key}) : super(key: PayrollRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<PayrollBloc, PayrollState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, PayrollRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<PayrollBloc, PayrollState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesPayrollUpdateTitle:
S.of(context).pageEntitiesPayrollCreateTitle;
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
          paymentDateField(),
          payPeriodField(),
          totalHoursWorkedField(),
          grossPayField(),
          netPayField(),
          totalTaxField(),
          payrollStatusField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget paymentDateField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.paymentDate != current.paymentDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<PayrollBloc>().paymentDateController,
                onChanged: (value) { context.bloc<PayrollBloc>().add(PaymentDateChanged(paymentDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesPayrollPaymentDateField,),
                onShowPicker: (context, currentValue) {
                  return showDatePicker(
                      locale: Locale(S.of(context).locale),
                      context: context,
                      firstDate: DateTime(1950),
                      initialDate: currentValue ?? DateTime.now(),
                      lastDate: DateTime(2050));
                },
              );
            }
        );
      }
      Widget payPeriodField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.payPeriod != current.payPeriod,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PayrollBloc>().payPeriodController,
                  onChanged: (value) { context.bloc<PayrollBloc>()
                    .add(PayPeriodChanged(payPeriod:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPayrollPayPeriodField));
            }
        );
      }
      Widget totalHoursWorkedField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.totalHoursWorked != current.totalHoursWorked,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PayrollBloc>().totalHoursWorkedController,
                  onChanged: (value) { context.bloc<PayrollBloc>()
                    .add(TotalHoursWorkedChanged(totalHoursWorked:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPayrollTotalHoursWorkedField));
            }
        );
      }
      Widget grossPayField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.grossPay != current.grossPay,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PayrollBloc>().grossPayController,
                  onChanged: (value) { context.bloc<PayrollBloc>()
                    .add(GrossPayChanged(grossPay:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPayrollGrossPayField));
            }
        );
      }
      Widget netPayField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.netPay != current.netPay,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PayrollBloc>().netPayController,
                  onChanged: (value) { context.bloc<PayrollBloc>()
                    .add(NetPayChanged(netPay:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPayrollNetPayField));
            }
        );
      }
      Widget totalTaxField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.totalTax != current.totalTax,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PayrollBloc>().totalTaxController,
                  onChanged: (value) { context.bloc<PayrollBloc>()
                    .add(TotalTaxChanged(totalTax:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPayrollTotalTaxField));
            }
        );
      }
      Widget payrollStatusField() {
        return BlocBuilder<PayrollBloc,PayrollState>(
            buildWhen: (previous, current) => previous.payrollStatus != current.payrollStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesPayrollPayrollStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<PayrollStatus>(
                        value: state.payrollStatus.value,
                        onChanged: (value) { context.bloc<PayrollBloc>().add(PayrollStatusChanged(payrollStatus: value)); },
                        items: createDropdownPayrollStatusItems(PayrollStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget createdDateField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<PayrollBloc>().createdDateController,
                onChanged: (value) { context.bloc<PayrollBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesPayrollCreatedDateField,),
                onShowPicker: (context, currentValue) {
                  return showDatePicker(
                      locale: Locale(S.of(context).locale),
                      context: context,
                      firstDate: DateTime(1950),
                      initialDate: currentValue ?? DateTime.now(),
                      lastDate: DateTime(2050));
                },
              );
            }
        );
      }
      Widget lastUpdatedDateField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<PayrollBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<PayrollBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesPayrollLastUpdatedDateField,),
                onShowPicker: (context, currentValue) {
                  return showDatePicker(
                      locale: Locale(S.of(context).locale),
                      context: context,
                      firstDate: DateTime(1950),
                      initialDate: currentValue ?? DateTime.now(),
                      lastDate: DateTime(2050));
                },
              );
            }
        );
      }
      Widget clientIdField() {
        return BlocBuilder<PayrollBloc, PayrollState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PayrollBloc>().clientIdController,
                  onChanged: (value) { context.bloc<PayrollBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPayrollClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<PayrollBloc,PayrollState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesPayrollHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<PayrollBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<PayrollStatus>> createDropdownPayrollStatusItems(List<PayrollStatus> payrollStatuss) {
        List<DropdownMenuItem<PayrollStatus>> payrollStatusDropDown = [];
    
        for (PayrollStatus payrollStatus in payrollStatuss) {
          DropdownMenuItem<PayrollStatus> dropdown = DropdownMenuItem<PayrollStatus>(
              value: payrollStatus, child: Text(payrollStatus.toString()));
              payrollStatusDropDown.add(dropdown);
        }
    
        return payrollStatusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<PayrollBloc, PayrollState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(PayrollState state, BuildContext context) {
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
    return BlocBuilder<PayrollBloc, PayrollState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<PayrollBloc>().add(PayrollFormSubmitted()) : null,
          );
        }
    );
  }
}
