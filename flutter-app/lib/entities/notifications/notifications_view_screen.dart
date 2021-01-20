import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/notifications/bloc/notifications_bloc.dart';
import 'package:amcCarePlanner/entities/notifications/notifications_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'notifications_route.dart';

class NotificationsViewScreen extends StatelessWidget {
  NotificationsViewScreen({Key key}) : super(key: NotificationsRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesNotificationsViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<NotificationsBloc, NotificationsState>(
              buildWhen: (previous, current) => previous.loadedNotifications != current.loadedNotifications,
              builder: (context, state) {
                return Visibility(
                  visible: state.notificationsStatusUI == NotificationsStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    notificationsCard(state.loadedNotifications, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, NotificationsRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget notificationsCard(Notifications notifications, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + notifications.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Title : ' + notifications.title.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Body : ' + notifications.body.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Notification Date : ' + (notifications?.notificationDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(notifications.notificationDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Image Url : ' + notifications.imageUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Sender Id : ' + notifications.senderId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Receiver Id : ' + notifications.receiverId.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (notifications?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(notifications.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (notifications?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(notifications.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + notifications.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + notifications.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
