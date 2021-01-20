import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/service_order/service_order_model.dart';
import 'package:amcCarePlanner/entities/service_order/service_order_repository.dart';
import 'package:amcCarePlanner/entities/service_order/bloc/service_order_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'service_order_events.dart';
part 'service_order_state.dart';

class ServiceOrderBloc extends Bloc<ServiceOrderEvent, ServiceOrderState> {
  final ServiceOrderRepository _serviceOrderRepository;

  final titleController = TextEditingController();
  final serviceDescriptionController = TextEditingController();
  final serviceHourlyRateController = TextEditingController();
  final clientIdController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();

  ServiceOrderBloc({@required ServiceOrderRepository serviceOrderRepository}) : assert(serviceOrderRepository != null),
        _serviceOrderRepository = serviceOrderRepository, 
  super(ServiceOrderState(null,null,));

  @override
  void onTransition(Transition<ServiceOrderEvent, ServiceOrderState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ServiceOrderState> mapEventToState(ServiceOrderEvent event) async* {
    if (event is InitServiceOrderList) {
      yield* onInitList(event);
    } else if (event is ServiceOrderFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadServiceOrderByIdForEdit) {
      yield* onLoadServiceOrderIdForEdit(event);
    } else if (event is DeleteServiceOrderById) {
      yield* onDeleteServiceOrderId(event);
    } else if (event is LoadServiceOrderByIdForView) {
      yield* onLoadServiceOrderIdForView(event);
    }else if (event is TitleChanged){
      yield* onTitleChange(event);
    }else if (event is ServiceDescriptionChanged){
      yield* onServiceDescriptionChange(event);
    }else if (event is ServiceHourlyRateChanged){
      yield* onServiceHourlyRateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ServiceOrderState> onInitList(InitServiceOrderList event) async* {
    yield this.state.copyWith(serviceOrderStatusUI: ServiceOrderStatusUI.loading);
    List<ServiceOrder> serviceOrders = await _serviceOrderRepository.getAllServiceOrders();
    yield this.state.copyWith(serviceOrders: serviceOrders, serviceOrderStatusUI: ServiceOrderStatusUI.done);
  }

  Stream<ServiceOrderState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ServiceOrder result;
        if(this.state.editMode) {
          ServiceOrder newServiceOrder = ServiceOrder(state.loadedServiceOrder.id,
            this.state.title.value,  
            this.state.serviceDescription.value,  
            this.state.serviceHourlyRate.value,  
            this.state.clientId.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _serviceOrderRepository.update(newServiceOrder);
        } else {
          ServiceOrder newServiceOrder = ServiceOrder(null,
            this.state.title.value,  
            this.state.serviceDescription.value,  
            this.state.serviceHourlyRate.value,  
            this.state.clientId.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _serviceOrderRepository.create(newServiceOrder);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<ServiceOrderState> onLoadServiceOrderIdForEdit(LoadServiceOrderByIdForEdit event) async* {
    yield this.state.copyWith(serviceOrderStatusUI: ServiceOrderStatusUI.loading);
    ServiceOrder loadedServiceOrder = await _serviceOrderRepository.getServiceOrder(event.id);

    final title = TitleInput.dirty(loadedServiceOrder?.title != null ? loadedServiceOrder.title: '');
    final serviceDescription = ServiceDescriptionInput.dirty(loadedServiceOrder?.serviceDescription != null ? loadedServiceOrder.serviceDescription: '');
    final serviceHourlyRate = ServiceHourlyRateInput.dirty(loadedServiceOrder?.serviceHourlyRate != null ? loadedServiceOrder.serviceHourlyRate: '');
    final clientId = ClientIdInput.dirty(loadedServiceOrder?.clientId != null ? loadedServiceOrder.clientId: 0);
    final createdDate = CreatedDateInput.dirty(loadedServiceOrder?.createdDate != null ? loadedServiceOrder.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedServiceOrder?.lastUpdatedDate != null ? loadedServiceOrder.lastUpdatedDate: null);
    final hasExtraData = HasExtraDataInput.dirty(loadedServiceOrder?.hasExtraData != null ? loadedServiceOrder.hasExtraData: false);

    yield this.state.copyWith(loadedServiceOrder: loadedServiceOrder, editMode: true,
      title: title,
      serviceDescription: serviceDescription,
      serviceHourlyRate: serviceHourlyRate,
      clientId: clientId,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      hasExtraData: hasExtraData,
    serviceOrderStatusUI: ServiceOrderStatusUI.done);

    titleController.text = loadedServiceOrder.title;
    serviceDescriptionController.text = loadedServiceOrder.serviceDescription;
    serviceHourlyRateController.text = loadedServiceOrder.serviceHourlyRate;
    clientIdController.text = loadedServiceOrder.clientId.toString();
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceOrder?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceOrder?.lastUpdatedDate);
  }

  Stream<ServiceOrderState> onDeleteServiceOrderId(DeleteServiceOrderById event) async* {
    try {
      await _serviceOrderRepository.delete(event.id);
      this.add(InitServiceOrderList());
      yield this.state.copyWith(deleteStatus: ServiceOrderDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ServiceOrderDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ServiceOrderDeleteStatus.none);
  }

  Stream<ServiceOrderState> onLoadServiceOrderIdForView(LoadServiceOrderByIdForView event) async* {
    yield this.state.copyWith(serviceOrderStatusUI: ServiceOrderStatusUI.loading);
    try {
      ServiceOrder loadedServiceOrder = await _serviceOrderRepository.getServiceOrder(event.id);
      yield this.state.copyWith(loadedServiceOrder: loadedServiceOrder, serviceOrderStatusUI: ServiceOrderStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedServiceOrder: null, serviceOrderStatusUI: ServiceOrderStatusUI.error);
    }
  }


  Stream<ServiceOrderState> onTitleChange(TitleChanged event) async* {
    final title = TitleInput.dirty(event.title);
    yield this.state.copyWith(
      title: title,
      formStatus: Formz.validate([
        title,
      this.state.serviceDescription,
      this.state.serviceHourlyRate,
      this.state.clientId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceOrderState> onServiceDescriptionChange(ServiceDescriptionChanged event) async* {
    final serviceDescription = ServiceDescriptionInput.dirty(event.serviceDescription);
    yield this.state.copyWith(
      serviceDescription: serviceDescription,
      formStatus: Formz.validate([
      this.state.title,
        serviceDescription,
      this.state.serviceHourlyRate,
      this.state.clientId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceOrderState> onServiceHourlyRateChange(ServiceHourlyRateChanged event) async* {
    final serviceHourlyRate = ServiceHourlyRateInput.dirty(event.serviceHourlyRate);
    yield this.state.copyWith(
      serviceHourlyRate: serviceHourlyRate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.serviceDescription,
        serviceHourlyRate,
      this.state.clientId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceOrderState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.title,
      this.state.serviceDescription,
      this.state.serviceHourlyRate,
        clientId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceOrderState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.serviceDescription,
      this.state.serviceHourlyRate,
      this.state.clientId,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceOrderState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.serviceDescription,
      this.state.serviceHourlyRate,
      this.state.clientId,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceOrderState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.title,
      this.state.serviceDescription,
      this.state.serviceHourlyRate,
      this.state.clientId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    titleController.dispose();
    serviceDescriptionController.dispose();
    serviceHourlyRateController.dispose();
    clientIdController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    return super.close();
  }

}