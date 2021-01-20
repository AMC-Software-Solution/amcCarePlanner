import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/employee_availability/employee_availability_model.dart';
import 'package:amcCarePlanner/entities/employee_availability/employee_availability_repository.dart';
import 'package:amcCarePlanner/entities/employee_availability/bloc/employee_availability_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'employee_availability_events.dart';
part 'employee_availability_state.dart';

class EmployeeAvailabilityBloc extends Bloc<EmployeeAvailabilityEvent, EmployeeAvailabilityState> {
  final EmployeeAvailabilityRepository _employeeAvailabilityRepository;

  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EmployeeAvailabilityBloc({@required EmployeeAvailabilityRepository employeeAvailabilityRepository}) : assert(employeeAvailabilityRepository != null),
        _employeeAvailabilityRepository = employeeAvailabilityRepository, 
  super(EmployeeAvailabilityState(null,));

  @override
  void onTransition(Transition<EmployeeAvailabilityEvent, EmployeeAvailabilityState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EmployeeAvailabilityState> mapEventToState(EmployeeAvailabilityEvent event) async* {
    if (event is InitEmployeeAvailabilityList) {
      yield* onInitList(event);
    } else if (event is EmployeeAvailabilityFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEmployeeAvailabilityByIdForEdit) {
      yield* onLoadEmployeeAvailabilityIdForEdit(event);
    } else if (event is DeleteEmployeeAvailabilityById) {
      yield* onDeleteEmployeeAvailabilityId(event);
    } else if (event is LoadEmployeeAvailabilityByIdForView) {
      yield* onLoadEmployeeAvailabilityIdForView(event);
    }else if (event is AvailableForWorkChanged){
      yield* onAvailableForWorkChange(event);
    }else if (event is AvailableMondayChanged){
      yield* onAvailableMondayChange(event);
    }else if (event is AvailableTuesdayChanged){
      yield* onAvailableTuesdayChange(event);
    }else if (event is AvailableWednesdayChanged){
      yield* onAvailableWednesdayChange(event);
    }else if (event is AvailableThursdayChanged){
      yield* onAvailableThursdayChange(event);
    }else if (event is AvailableFridayChanged){
      yield* onAvailableFridayChange(event);
    }else if (event is AvailableSaturdayChanged){
      yield* onAvailableSaturdayChange(event);
    }else if (event is AvailableSundayChanged){
      yield* onAvailableSundayChange(event);
    }else if (event is PreferredShiftChanged){
      yield* onPreferredShiftChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }  }

  Stream<EmployeeAvailabilityState> onInitList(InitEmployeeAvailabilityList event) async* {
    yield this.state.copyWith(employeeAvailabilityStatusUI: EmployeeAvailabilityStatusUI.loading);
    List<EmployeeAvailability> employeeAvailabilities = await _employeeAvailabilityRepository.getAllEmployeeAvailabilities();
    yield this.state.copyWith(employeeAvailabilities: employeeAvailabilities, employeeAvailabilityStatusUI: EmployeeAvailabilityStatusUI.done);
  }

  Stream<EmployeeAvailabilityState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        EmployeeAvailability result;
        if(this.state.editMode) {
          EmployeeAvailability newEmployeeAvailability = EmployeeAvailability(state.loadedEmployeeAvailability.id,
            this.state.availableForWork.value,  
            this.state.availableMonday.value,  
            this.state.availableTuesday.value,  
            this.state.availableWednesday.value,  
            this.state.availableThursday.value,  
            this.state.availableFriday.value,  
            this.state.availableSaturday.value,  
            this.state.availableSunday.value,  
            this.state.preferredShift.value,  
            this.state.hasExtraData.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            null, 
          );

          result = await _employeeAvailabilityRepository.update(newEmployeeAvailability);
        } else {
          EmployeeAvailability newEmployeeAvailability = EmployeeAvailability(null,
            this.state.availableForWork.value,  
            this.state.availableMonday.value,  
            this.state.availableTuesday.value,  
            this.state.availableWednesday.value,  
            this.state.availableThursday.value,  
            this.state.availableFriday.value,  
            this.state.availableSaturday.value,  
            this.state.availableSunday.value,  
            this.state.preferredShift.value,  
            this.state.hasExtraData.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            null, 
          );

          result = await _employeeAvailabilityRepository.create(newEmployeeAvailability);
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

  Stream<EmployeeAvailabilityState> onLoadEmployeeAvailabilityIdForEdit(LoadEmployeeAvailabilityByIdForEdit event) async* {
    yield this.state.copyWith(employeeAvailabilityStatusUI: EmployeeAvailabilityStatusUI.loading);
    EmployeeAvailability loadedEmployeeAvailability = await _employeeAvailabilityRepository.getEmployeeAvailability(event.id);

    final availableForWork = AvailableForWorkInput.dirty(loadedEmployeeAvailability?.availableForWork != null ? loadedEmployeeAvailability.availableForWork: false);
    final availableMonday = AvailableMondayInput.dirty(loadedEmployeeAvailability?.availableMonday != null ? loadedEmployeeAvailability.availableMonday: false);
    final availableTuesday = AvailableTuesdayInput.dirty(loadedEmployeeAvailability?.availableTuesday != null ? loadedEmployeeAvailability.availableTuesday: false);
    final availableWednesday = AvailableWednesdayInput.dirty(loadedEmployeeAvailability?.availableWednesday != null ? loadedEmployeeAvailability.availableWednesday: false);
    final availableThursday = AvailableThursdayInput.dirty(loadedEmployeeAvailability?.availableThursday != null ? loadedEmployeeAvailability.availableThursday: false);
    final availableFriday = AvailableFridayInput.dirty(loadedEmployeeAvailability?.availableFriday != null ? loadedEmployeeAvailability.availableFriday: false);
    final availableSaturday = AvailableSaturdayInput.dirty(loadedEmployeeAvailability?.availableSaturday != null ? loadedEmployeeAvailability.availableSaturday: false);
    final availableSunday = AvailableSundayInput.dirty(loadedEmployeeAvailability?.availableSunday != null ? loadedEmployeeAvailability.availableSunday: false);
    final preferredShift = PreferredShiftInput.dirty(loadedEmployeeAvailability?.preferredShift != null ? loadedEmployeeAvailability.preferredShift: null);
    final hasExtraData = HasExtraDataInput.dirty(loadedEmployeeAvailability?.hasExtraData != null ? loadedEmployeeAvailability.hasExtraData: false);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEmployeeAvailability?.lastUpdatedDate != null ? loadedEmployeeAvailability.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEmployeeAvailability?.clientId != null ? loadedEmployeeAvailability.clientId: 0);

    yield this.state.copyWith(loadedEmployeeAvailability: loadedEmployeeAvailability, editMode: true,
      availableForWork: availableForWork,
      availableMonday: availableMonday,
      availableTuesday: availableTuesday,
      availableWednesday: availableWednesday,
      availableThursday: availableThursday,
      availableFriday: availableFriday,
      availableSaturday: availableSaturday,
      availableSunday: availableSunday,
      preferredShift: preferredShift,
      hasExtraData: hasExtraData,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
    employeeAvailabilityStatusUI: EmployeeAvailabilityStatusUI.done);

    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeAvailability?.lastUpdatedDate);
    clientIdController.text = loadedEmployeeAvailability.clientId.toString();
  }

  Stream<EmployeeAvailabilityState> onDeleteEmployeeAvailabilityId(DeleteEmployeeAvailabilityById event) async* {
    try {
      await _employeeAvailabilityRepository.delete(event.id);
      this.add(InitEmployeeAvailabilityList());
      yield this.state.copyWith(deleteStatus: EmployeeAvailabilityDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EmployeeAvailabilityDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EmployeeAvailabilityDeleteStatus.none);
  }

  Stream<EmployeeAvailabilityState> onLoadEmployeeAvailabilityIdForView(LoadEmployeeAvailabilityByIdForView event) async* {
    yield this.state.copyWith(employeeAvailabilityStatusUI: EmployeeAvailabilityStatusUI.loading);
    try {
      EmployeeAvailability loadedEmployeeAvailability = await _employeeAvailabilityRepository.getEmployeeAvailability(event.id);
      yield this.state.copyWith(loadedEmployeeAvailability: loadedEmployeeAvailability, employeeAvailabilityStatusUI: EmployeeAvailabilityStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEmployeeAvailability: null, employeeAvailabilityStatusUI: EmployeeAvailabilityStatusUI.error);
    }
  }


  Stream<EmployeeAvailabilityState> onAvailableForWorkChange(AvailableForWorkChanged event) async* {
    final availableForWork = AvailableForWorkInput.dirty(event.availableForWork);
    yield this.state.copyWith(
      availableForWork: availableForWork,
      formStatus: Formz.validate([
        availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onAvailableMondayChange(AvailableMondayChanged event) async* {
    final availableMonday = AvailableMondayInput.dirty(event.availableMonday);
    yield this.state.copyWith(
      availableMonday: availableMonday,
      formStatus: Formz.validate([
      this.state.availableForWork,
        availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onAvailableTuesdayChange(AvailableTuesdayChanged event) async* {
    final availableTuesday = AvailableTuesdayInput.dirty(event.availableTuesday);
    yield this.state.copyWith(
      availableTuesday: availableTuesday,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
        availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onAvailableWednesdayChange(AvailableWednesdayChanged event) async* {
    final availableWednesday = AvailableWednesdayInput.dirty(event.availableWednesday);
    yield this.state.copyWith(
      availableWednesday: availableWednesday,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
        availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onAvailableThursdayChange(AvailableThursdayChanged event) async* {
    final availableThursday = AvailableThursdayInput.dirty(event.availableThursday);
    yield this.state.copyWith(
      availableThursday: availableThursday,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
        availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onAvailableFridayChange(AvailableFridayChanged event) async* {
    final availableFriday = AvailableFridayInput.dirty(event.availableFriday);
    yield this.state.copyWith(
      availableFriday: availableFriday,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
        availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onAvailableSaturdayChange(AvailableSaturdayChanged event) async* {
    final availableSaturday = AvailableSaturdayInput.dirty(event.availableSaturday);
    yield this.state.copyWith(
      availableSaturday: availableSaturday,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
        availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onAvailableSundayChange(AvailableSundayChanged event) async* {
    final availableSunday = AvailableSundayInput.dirty(event.availableSunday);
    yield this.state.copyWith(
      availableSunday: availableSunday,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
        availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onPreferredShiftChange(PreferredShiftChanged event) async* {
    final preferredShift = PreferredShiftInput.dirty(event.preferredShift);
    yield this.state.copyWith(
      preferredShift: preferredShift,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
        preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
        hasExtraData,
      this.state.lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
        lastUpdatedDate,
      this.state.clientId,
      ]),
    );
  }
  Stream<EmployeeAvailabilityState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.availableForWork,
      this.state.availableMonday,
      this.state.availableTuesday,
      this.state.availableWednesday,
      this.state.availableThursday,
      this.state.availableFriday,
      this.state.availableSaturday,
      this.state.availableSunday,
      this.state.preferredShift,
      this.state.hasExtraData,
      this.state.lastUpdatedDate,
        clientId,
      ]),
    );
  }

  @override
  Future<void> close() {
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}