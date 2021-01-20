import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_holiday/bloc/employee_holiday_bloc.dart';
import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'employee_holiday_route.dart';

class EmployeeHolidayViewScreen extends StatelessWidget {
  EmployeeHolidayViewScreen({Key key}) : super(key: EmployeeHolidayRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEmployeeHolidayViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
              buildWhen: (previous, current) => previous.loadedEmployeeHoliday != current.loadedEmployeeHoliday,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeHolidayStatusUI == EmployeeHolidayStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    employeeHolidayCard(state.loadedEmployeeHoliday, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeHolidayRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget employeeHolidayCard(EmployeeHoliday employeeHoliday, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + employeeHoliday.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + employeeHoliday.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Start Date : ' + (employeeHoliday?.startDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeHoliday.startDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('End Date : ' + (employeeHoliday?.endDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeHoliday.endDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Employee Holiday Type : ' + employeeHoliday.employeeHolidayType.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Approved Date : ' + (employeeHoliday?.approvedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeHoliday.approvedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Requested Date : ' + (employeeHoliday?.requestedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeHoliday.requestedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Holiday Status : ' + employeeHoliday.holidayStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + employeeHoliday.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Rejection Reason : ' + employeeHoliday.rejectionReason.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (employeeHoliday?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeHoliday.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (employeeHoliday?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeHoliday.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + employeeHoliday.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + employeeHoliday.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
