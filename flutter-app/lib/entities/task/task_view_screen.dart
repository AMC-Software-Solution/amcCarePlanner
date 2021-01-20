import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/task/bloc/task_bloc.dart';
import 'package:amcCarePlanner/entities/task/task_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'task_route.dart';

class TaskViewScreen extends StatelessWidget {
  TaskViewScreen({Key key}) : super(key: TaskRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesTaskViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<TaskBloc, TaskState>(
              buildWhen: (previous, current) => previous.loadedTask != current.loadedTask,
              builder: (context, state) {
                return Visibility(
                  visible: state.taskStatusUI == TaskStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    taskCard(state.loadedTask, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, TaskRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget taskCard(Task task, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + task.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Task Name : ' + task.taskName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + task.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Date Of Task : ' + (task?.dateOfTask != null ? DateFormat.yMMMMd(S.of(context).locale).format(task.dateOfTask) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Start Time : ' + task.startTime.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('End Time : ' + task.endTime.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Status : ' + task.status.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (task?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(task.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (task?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(task.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + task.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + task.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
