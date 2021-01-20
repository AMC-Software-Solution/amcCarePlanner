import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/invoice/bloc/invoice_bloc.dart';
import 'package:amcCarePlanner/entities/invoice/invoice_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'invoice_route.dart';

class InvoiceViewScreen extends StatelessWidget {
  InvoiceViewScreen({Key key}) : super(key: InvoiceRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesInvoiceViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<InvoiceBloc, InvoiceState>(
              buildWhen: (previous, current) => previous.loadedInvoice != current.loadedInvoice,
              builder: (context, state) {
                return Visibility(
                  visible: state.invoiceStatusUI == InvoiceStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    invoiceCard(state.loadedInvoice, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, InvoiceRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget invoiceCard(Invoice invoice, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + invoice.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Total Amount : ' + invoice.totalAmount.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + invoice.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Invoice Number : ' + invoice.invoiceNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Generated Date : ' + (invoice?.generatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(invoice.generatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Due Date : ' + (invoice?.dueDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(invoice.dueDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Payment Date : ' + (invoice?.paymentDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(invoice.paymentDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Invoice Status : ' + invoice.invoiceStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Tax : ' + invoice.tax.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 1 : ' + invoice.attribute1.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 2 : ' + invoice.attribute2.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 3 : ' + invoice.attribute3.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 4 : ' + invoice.attribute4.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 5 : ' + invoice.attribute5.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 6 : ' + invoice.attribute6.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 7 : ' + invoice.attribute7.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (invoice?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(invoice.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (invoice?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(invoice.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + invoice.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + invoice.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
