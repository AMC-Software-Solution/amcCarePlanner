import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/service_user_location/service_user_location_model.dart';
import 'package:amcCarePlanner/entities/service_user_location/service_user_location_repository.dart';
import 'package:amcCarePlanner/entities/service_user_location/bloc/service_user_location_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'service_user_location_events.dart';
part 'service_user_location_state.dart';

class ServiceUserLocationBloc extends Bloc<ServiceUserLocationEvent, ServiceUserLocationState> {
  final ServiceUserLocationRepository _serviceUserLocationRepository;

  final latitudeController = TextEditingController();
  final longitudeController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  ServiceUserLocationBloc({@required ServiceUserLocationRepository serviceUserLocationRepository}) : assert(serviceUserLocationRepository != null),
        _serviceUserLocationRepository = serviceUserLocationRepository, 
  super(ServiceUserLocationState(null,null,));

  @override
  void onTransition(Transition<ServiceUserLocationEvent, ServiceUserLocationState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ServiceUserLocationState> mapEventToState(ServiceUserLocationEvent event) async* {
    if (event is InitServiceUserLocationList) {
      yield* onInitList(event);
    } else if (event is ServiceUserLocationFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadServiceUserLocationByIdForEdit) {
      yield* onLoadServiceUserLocationIdForEdit(event);
    } else if (event is DeleteServiceUserLocationById) {
      yield* onDeleteServiceUserLocationId(event);
    } else if (event is LoadServiceUserLocationByIdForView) {
      yield* onLoadServiceUserLocationIdForView(event);
    }else if (event is LatitudeChanged){
      yield* onLatitudeChange(event);
    }else if (event is LongitudeChanged){
      yield* onLongitudeChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ServiceUserLocationState> onInitList(InitServiceUserLocationList event) async* {
    yield this.state.copyWith(serviceUserLocationStatusUI: ServiceUserLocationStatusUI.loading);
    List<ServiceUserLocation> serviceUserLocations = await _serviceUserLocationRepository.getAllServiceUserLocations();
    yield this.state.copyWith(serviceUserLocations: serviceUserLocations, serviceUserLocationStatusUI: ServiceUserLocationStatusUI.done);
  }

  Stream<ServiceUserLocationState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ServiceUserLocation result;
        if(this.state.editMode) {
          ServiceUserLocation newServiceUserLocation = ServiceUserLocation(state.loadedServiceUserLocation.id,
            this.state.latitude.value,  
            this.state.longitude.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _serviceUserLocationRepository.update(newServiceUserLocation);
        } else {
          ServiceUserLocation newServiceUserLocation = ServiceUserLocation(null,
            this.state.latitude.value,  
            this.state.longitude.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _serviceUserLocationRepository.create(newServiceUserLocation);
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

  Stream<ServiceUserLocationState> onLoadServiceUserLocationIdForEdit(LoadServiceUserLocationByIdForEdit event) async* {
    yield this.state.copyWith(serviceUserLocationStatusUI: ServiceUserLocationStatusUI.loading);
    ServiceUserLocation loadedServiceUserLocation = await _serviceUserLocationRepository.getServiceUserLocation(event.id);

    final latitude = LatitudeInput.dirty(loadedServiceUserLocation?.latitude != null ? loadedServiceUserLocation.latitude: '');
    final longitude = LongitudeInput.dirty(loadedServiceUserLocation?.longitude != null ? loadedServiceUserLocation.longitude: '');
    final createdDate = CreatedDateInput.dirty(loadedServiceUserLocation?.createdDate != null ? loadedServiceUserLocation.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedServiceUserLocation?.lastUpdatedDate != null ? loadedServiceUserLocation.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedServiceUserLocation?.clientId != null ? loadedServiceUserLocation.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedServiceUserLocation?.hasExtraData != null ? loadedServiceUserLocation.hasExtraData: false);

    yield this.state.copyWith(loadedServiceUserLocation: loadedServiceUserLocation, editMode: true,
      latitude: latitude,
      longitude: longitude,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    serviceUserLocationStatusUI: ServiceUserLocationStatusUI.done);

    latitudeController.text = loadedServiceUserLocation.latitude;
    longitudeController.text = loadedServiceUserLocation.longitude;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUserLocation?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUserLocation?.lastUpdatedDate);
    clientIdController.text = loadedServiceUserLocation.clientId.toString();
  }

  Stream<ServiceUserLocationState> onDeleteServiceUserLocationId(DeleteServiceUserLocationById event) async* {
    try {
      await _serviceUserLocationRepository.delete(event.id);
      this.add(InitServiceUserLocationList());
      yield this.state.copyWith(deleteStatus: ServiceUserLocationDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ServiceUserLocationDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ServiceUserLocationDeleteStatus.none);
  }

  Stream<ServiceUserLocationState> onLoadServiceUserLocationIdForView(LoadServiceUserLocationByIdForView event) async* {
    yield this.state.copyWith(serviceUserLocationStatusUI: ServiceUserLocationStatusUI.loading);
    try {
      ServiceUserLocation loadedServiceUserLocation = await _serviceUserLocationRepository.getServiceUserLocation(event.id);
      yield this.state.copyWith(loadedServiceUserLocation: loadedServiceUserLocation, serviceUserLocationStatusUI: ServiceUserLocationStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedServiceUserLocation: null, serviceUserLocationStatusUI: ServiceUserLocationStatusUI.error);
    }
  }


  Stream<ServiceUserLocationState> onLatitudeChange(LatitudeChanged event) async* {
    final latitude = LatitudeInput.dirty(event.latitude);
    yield this.state.copyWith(
      latitude: latitude,
      formStatus: Formz.validate([
        latitude,
      this.state.longitude,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserLocationState> onLongitudeChange(LongitudeChanged event) async* {
    final longitude = LongitudeInput.dirty(event.longitude);
    yield this.state.copyWith(
      longitude: longitude,
      formStatus: Formz.validate([
      this.state.latitude,
        longitude,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserLocationState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.latitude,
      this.state.longitude,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserLocationState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.latitude,
      this.state.longitude,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserLocationState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.latitude,
      this.state.longitude,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserLocationState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.latitude,
      this.state.longitude,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    latitudeController.dispose();
    longitudeController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}