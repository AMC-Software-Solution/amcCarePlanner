part of 'service_order_bloc.dart';

enum ServiceOrderStatusUI {init, loading, error, done}
enum ServiceOrderDeleteStatus {ok, ko, none}

class ServiceOrderState extends Equatable {
  final List<ServiceOrder> serviceOrders;
  final ServiceOrder loadedServiceOrder;
  final bool editMode;
  final ServiceOrderDeleteStatus deleteStatus;
  final ServiceOrderStatusUI serviceOrderStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TitleInput title;
  final ServiceDescriptionInput serviceDescription;
  final ServiceHourlyRateInput serviceHourlyRate;
  final ClientIdInput clientId;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final HasExtraDataInput hasExtraData;

  
  ServiceOrderState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.serviceOrders = const [],
    this.serviceOrderStatusUI = ServiceOrderStatusUI.init,
    this.loadedServiceOrder = const ServiceOrder(0,'','','',0,null,null,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ServiceOrderDeleteStatus.none,
    this.title = const TitleInput.pure(),
    this.serviceDescription = const ServiceDescriptionInput.pure(),
    this.serviceHourlyRate = const ServiceHourlyRateInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ServiceOrderState copyWith({
    List<ServiceOrder> serviceOrders,
    ServiceOrderStatusUI serviceOrderStatusUI,
    bool editMode,
    ServiceOrderDeleteStatus deleteStatus,
    ServiceOrder loadedServiceOrder,
    FormzStatus formStatus,
    String generalNotificationKey,
    TitleInput title,
    ServiceDescriptionInput serviceDescription,
    ServiceHourlyRateInput serviceHourlyRate,
    ClientIdInput clientId,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    HasExtraDataInput hasExtraData,
  }) {
    return ServiceOrderState(
        createdDate,
        lastUpdatedDate,
      serviceOrders: serviceOrders ?? this.serviceOrders,
      serviceOrderStatusUI: serviceOrderStatusUI ?? this.serviceOrderStatusUI,
      loadedServiceOrder: loadedServiceOrder ?? this.loadedServiceOrder,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      title: title ?? this.title,
      serviceDescription: serviceDescription ?? this.serviceDescription,
      serviceHourlyRate: serviceHourlyRate ?? this.serviceHourlyRate,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [serviceOrders, serviceOrderStatusUI,
     loadedServiceOrder, editMode, deleteStatus, formStatus, generalNotificationKey, 
title,serviceDescription,serviceHourlyRate,clientId,createdDate,lastUpdatedDate,hasExtraData,];

  @override
  bool get stringify => true;
}
