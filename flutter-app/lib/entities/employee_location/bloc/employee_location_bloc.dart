import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/employee_location/employee_location_model.dart';
import 'package:amcCarePlanner/entities/employee_location/employee_location_repository.dart';
import 'package:amcCarePlanner/entities/employee_location/bloc/employee_location_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'employee_location_events.dart';
part 'employee_location_state.dart';

class EmployeeLocationBloc extends Bloc<EmployeeLocationEvent, EmployeeLocationState> {
  final EmployeeLocationRepository _employeeLocationRepository;

  final latitudeController = TextEditingController();
  final longitudeController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EmployeeLocationBloc({@required EmployeeLocationRepository employeeLocationRepository}) : assert(employeeLocationRepository != null),
        _employeeLocationRepository = employeeLocationRepository, 
  super(EmployeeLocationState(null,null,));

  @override
  void onTransition(Transition<EmployeeLocationEvent, EmployeeLocationState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EmployeeLocationState> mapEventToState(EmployeeLocationEvent event) async* {
    if (event is InitEmployeeLocationList) {
      yield* onInitList(event);
    } else if (event is EmployeeLocationFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEmployeeLocationByIdForEdit) {
      yield* onLoadEmployeeLocationIdForEdit(event);
    } else if (event is DeleteEmployeeLocationById) {
      yield* onDeleteEmployeeLocationId(event);
    } else if (event is LoadEmployeeLocationByIdForView) {
      yield* onLoadEmployeeLocationIdForView(event);
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

  Stream<EmployeeLocationState> onInitList(InitEmployeeLocationList event) async* {
    yield this.state.copyWith(employeeLocationStatusUI: EmployeeLocationStatusUI.loading);
    List<EmployeeLocation> employeeLocations = await _employeeLocationRepository.getAllEmployeeLocations();
    yield this.state.copyWith(employeeLocations: employeeLocations, employeeLocationStatusUI: EmployeeLocationStatusUI.done);
  }

  Stream<EmployeeLocationState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        EmployeeLocation result;
        if(this.state.editMode) {
          EmployeeLocation newEmployeeLocation = EmployeeLocation(state.loadedEmployeeLocation.id,
            this.state.latitude.value,  
            this.state.longitude.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _employeeLocationRepository.update(newEmployeeLocation);
        } else {
          EmployeeLocation newEmployeeLocation = EmployeeLocation(null,
            this.state.latitude.value,  
            this.state.longitude.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _employeeLocationRepository.create(newEmployeeLocation);
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

  Stream<EmployeeLocationState> onLoadEmployeeLocationIdForEdit(LoadEmployeeLocationByIdForEdit event) async* {
    yield this.state.copyWith(employeeLocationStatusUI: EmployeeLocationStatusUI.loading);
    EmployeeLocation loadedEmployeeLocation = await _employeeLocationRepository.getEmployeeLocation(event.id);

    final latitude = LatitudeInput.dirty(loadedEmployeeLocation?.latitude != null ? loadedEmployeeLocation.latitude: '');
    final longitude = LongitudeInput.dirty(loadedEmployeeLocation?.longitude != null ? loadedEmployeeLocation.longitude: '');
    final createdDate = CreatedDateInput.dirty(loadedEmployeeLocation?.createdDate != null ? loadedEmployeeLocation.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEmployeeLocation?.lastUpdatedDate != null ? loadedEmployeeLocation.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEmployeeLocation?.clientId != null ? loadedEmployeeLocation.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEmployeeLocation?.hasExtraData != null ? loadedEmployeeLocation.hasExtraData: false);

    yield this.state.copyWith(loadedEmployeeLocation: loadedEmployeeLocation, editMode: true,
      latitude: latitude,
      longitude: longitude,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    employeeLocationStatusUI: EmployeeLocationStatusUI.done);

    latitudeController.text = loadedEmployeeLocation.latitude;
    longitudeController.text = loadedEmployeeLocation.longitude;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeLocation?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeLocation?.lastUpdatedDate);
    clientIdController.text = loadedEmployeeLocation.clientId.toString();
  }

  Stream<EmployeeLocationState> onDeleteEmployeeLocationId(DeleteEmployeeLocationById event) async* {
    try {
      await _employeeLocationRepository.delete(event.id);
      this.add(InitEmployeeLocationList());
      yield this.state.copyWith(deleteStatus: EmployeeLocationDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EmployeeLocationDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EmployeeLocationDeleteStatus.none);
  }

  Stream<EmployeeLocationState> onLoadEmployeeLocationIdForView(LoadEmployeeLocationByIdForView event) async* {
    yield this.state.copyWith(employeeLocationStatusUI: EmployeeLocationStatusUI.loading);
    try {
      EmployeeLocation loadedEmployeeLocation = await _employeeLocationRepository.getEmployeeLocation(event.id);
      yield this.state.copyWith(loadedEmployeeLocation: loadedEmployeeLocation, employeeLocationStatusUI: EmployeeLocationStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEmployeeLocation: null, employeeLocationStatusUI: EmployeeLocationStatusUI.error);
    }
  }


  Stream<EmployeeLocationState> onLatitudeChange(LatitudeChanged event) async* {
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
  Stream<EmployeeLocationState> onLongitudeChange(LongitudeChanged event) async* {
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
  Stream<EmployeeLocationState> onCreatedDateChange(CreatedDateChanged event) async* {
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
  Stream<EmployeeLocationState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
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
  Stream<EmployeeLocationState> onClientIdChange(ClientIdChanged event) async* {
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
  Stream<EmployeeLocationState> onHasExtraDataChange(HasExtraDataChanged event) async* {
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