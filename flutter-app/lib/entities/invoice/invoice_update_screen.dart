import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/invoice/bloc/invoice_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/invoice/invoice_model.dart';
import 'invoice_route.dart';

class InvoiceUpdateScreen extends StatelessWidget {
  InvoiceUpdateScreen({Key key}) : super(key: InvoiceRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<InvoiceBloc, InvoiceState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, InvoiceRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<InvoiceBloc, InvoiceState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesInvoiceUpdateTitle:
S.of(context).pageEntitiesInvoiceCreateTitle;
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
          totalAmountField(),
          descriptionField(),
          invoiceNumberField(),
          generatedDateField(),
          dueDateField(),
          paymentDateField(),
          invoiceStatusField(),
          taxField(),
          attribute1Field(),
          attribute2Field(),
          attribute3Field(),
          attribute4Field(),
          attribute5Field(),
          attribute6Field(),
          attribute7Field(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget totalAmountField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.totalAmount != current.totalAmount,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().totalAmountController,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(TotalAmountChanged(totalAmount:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceTotalAmountField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().descriptionController,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceDescriptionField));
            }
        );
      }
      Widget invoiceNumberField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.invoiceNumber != current.invoiceNumber,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().invoiceNumberController,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(InvoiceNumberChanged(invoiceNumber:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceInvoiceNumberField));
            }
        );
      }
      Widget generatedDateField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.generatedDate != current.generatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<InvoiceBloc>().generatedDateController,
                onChanged: (value) { context.bloc<InvoiceBloc>().add(GeneratedDateChanged(generatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesInvoiceGeneratedDateField,),
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
      Widget dueDateField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.dueDate != current.dueDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<InvoiceBloc>().dueDateController,
                onChanged: (value) { context.bloc<InvoiceBloc>().add(DueDateChanged(dueDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesInvoiceDueDateField,),
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
      Widget paymentDateField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.paymentDate != current.paymentDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<InvoiceBloc>().paymentDateController,
                onChanged: (value) { context.bloc<InvoiceBloc>().add(PaymentDateChanged(paymentDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesInvoicePaymentDateField,),
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
      Widget invoiceStatusField() {
        return BlocBuilder<InvoiceBloc,InvoiceState>(
            buildWhen: (previous, current) => previous.invoiceStatus != current.invoiceStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesInvoiceInvoiceStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<InvoiceStatus>(
                        value: state.invoiceStatus.value,
                        onChanged: (value) { context.bloc<InvoiceBloc>().add(InvoiceStatusChanged(invoiceStatus: value)); },
                        items: createDropdownInvoiceStatusItems(InvoiceStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget taxField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.tax != current.tax,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().taxController,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(TaxChanged(tax:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceTaxField));
            }
        );
      }
      Widget attribute1Field() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.attribute1 != current.attribute1,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().attribute1Controller,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(Attribute1Changed(attribute1:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceAttribute1Field));
            }
        );
      }
      Widget attribute2Field() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.attribute2 != current.attribute2,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().attribute2Controller,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(Attribute2Changed(attribute2:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceAttribute2Field));
            }
        );
      }
      Widget attribute3Field() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.attribute3 != current.attribute3,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().attribute3Controller,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(Attribute3Changed(attribute3:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceAttribute3Field));
            }
        );
      }
      Widget attribute4Field() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.attribute4 != current.attribute4,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().attribute4Controller,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(Attribute4Changed(attribute4:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceAttribute4Field));
            }
        );
      }
      Widget attribute5Field() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.attribute5 != current.attribute5,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().attribute5Controller,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(Attribute5Changed(attribute5:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceAttribute5Field));
            }
        );
      }
      Widget attribute6Field() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.attribute6 != current.attribute6,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().attribute6Controller,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(Attribute6Changed(attribute6:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceAttribute6Field));
            }
        );
      }
      Widget attribute7Field() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.attribute7 != current.attribute7,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().attribute7Controller,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(Attribute7Changed(attribute7:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceAttribute7Field));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<InvoiceBloc>().createdDateController,
                onChanged: (value) { context.bloc<InvoiceBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesInvoiceCreatedDateField,),
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
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<InvoiceBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<InvoiceBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesInvoiceLastUpdatedDateField,),
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
        return BlocBuilder<InvoiceBloc, InvoiceState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<InvoiceBloc>().clientIdController,
                  onChanged: (value) { context.bloc<InvoiceBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesInvoiceClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<InvoiceBloc,InvoiceState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesInvoiceHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<InvoiceBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<InvoiceStatus>> createDropdownInvoiceStatusItems(List<InvoiceStatus> invoiceStatuss) {
        List<DropdownMenuItem<InvoiceStatus>> invoiceStatusDropDown = [];
    
        for (InvoiceStatus invoiceStatus in invoiceStatuss) {
          DropdownMenuItem<InvoiceStatus> dropdown = DropdownMenuItem<InvoiceStatus>(
              value: invoiceStatus, child: Text(invoiceStatus.toString()));
              invoiceStatusDropDown.add(dropdown);
        }
    
        return invoiceStatusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<InvoiceBloc, InvoiceState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(InvoiceState state, BuildContext context) {
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
    return BlocBuilder<InvoiceBloc, InvoiceState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<InvoiceBloc>().add(InvoiceFormSubmitted()) : null,
          );
        }
    );
  }
}
