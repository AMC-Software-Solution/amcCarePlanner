import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/payroll/bloc/payroll_bloc.dart';
import 'package:amcCarePlanner/entities/payroll/payroll_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'payroll_route.dart';

class PayrollViewScreen extends StatelessWidget {
  PayrollViewScreen({Key key}) : super(key: PayrollRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesPayrollViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<PayrollBloc, PayrollState>(
              buildWhen: (previous, current) => previous.loadedPayroll != current.loadedPayroll,
              builder: (context, state) {
                return Visibility(
                  visible: state.payrollStatusUI == PayrollStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    payrollCard(state.loadedPayroll, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, PayrollRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget payrollCard(Payroll payroll, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + payroll.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Payment Date : ' + (payroll?.paymentDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(payroll.paymentDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Pay Period : ' + payroll.payPeriod.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Total Hours Worked : ' + payroll.totalHoursWorked.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Gross Pay : ' + payroll.grossPay.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Net Pay : ' + payroll.netPay.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Total Tax : ' + payroll.totalTax.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Payroll Status : ' + payroll.payrollStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (payroll?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(payroll.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (payroll?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(payroll.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + payroll.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + payroll.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
