import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/timesheet/bloc/timesheet_bloc.dart';
import 'package:amcCarePlanner/entities/timesheet/timesheet_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'timesheet_route.dart';

class TimesheetViewScreen extends StatelessWidget {
  TimesheetViewScreen({Key key}) : super(key: TimesheetRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesTimesheetViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<TimesheetBloc, TimesheetState>(
              buildWhen: (previous, current) => previous.loadedTimesheet != current.loadedTimesheet,
              builder: (context, state) {
                return Visibility(
                  visible: state.timesheetStatusUI == TimesheetStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    timesheetCard(state.loadedTimesheet, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, TimesheetRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget timesheetCard(Timesheet timesheet, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + timesheet.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + timesheet.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Timesheet Date : ' + (timesheet?.timesheetDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(timesheet.timesheetDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Start Time : ' + timesheet.startTime.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('End Time : ' + timesheet.endTime.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Hours Worked : ' + timesheet.hoursWorked.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Break Start Time : ' + timesheet.breakStartTime.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Break End Time : ' + timesheet.breakEndTime.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + timesheet.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (timesheet?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(timesheet.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (timesheet?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(timesheet.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + timesheet.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + timesheet.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
