import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/employee_designation/employee_designation_model.dart';
import 'package:amcCarePlanner/entities/employee_designation/employee_designation_repository.dart';
import 'package:amcCarePlanner/entities/employee_designation/bloc/employee_designation_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'employee_designation_events.dart';
part 'employee_designation_state.dart';

class EmployeeDesignationBloc extends Bloc<EmployeeDesignationEvent, EmployeeDesignationState> {
  final EmployeeDesignationRepository _employeeDesignationRepository;

  final designationController = TextEditingController();
  final descriptionController = TextEditingController();
  final designationDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EmployeeDesignationBloc({@required EmployeeDesignationRepository employeeDesignationRepository}) : assert(employeeDesignationRepository != null),
        _employeeDesignationRepository = employeeDesignationRepository, 
  super(EmployeeDesignationState());

  @override
  void onTransition(Transition<EmployeeDesignationEvent, EmployeeDesignationState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EmployeeDesignationState> mapEventToState(EmployeeDesignationEvent event) async* {
    if (event is InitEmployeeDesignationList) {
      yield* onInitList(event);
    } else if (event is EmployeeDesignationFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEmployeeDesignationByIdForEdit) {
      yield* onLoadEmployeeDesignationIdForEdit(event);
    } else if (event is DeleteEmployeeDesignationById) {
      yield* onDeleteEmployeeDesignationId(event);
    } else if (event is LoadEmployeeDesignationByIdForView) {
      yield* onLoadEmployeeDesignationIdForView(event);
    }else if (event is DesignationChanged){
      yield* onDesignationChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is DesignationDateChanged){
      yield* onDesignationDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<EmployeeDesignationState> onInitList(InitEmployeeDesignationList event) async* {
    yield this.state.copyWith(employeeDesignationStatusUI: EmployeeDesignationStatusUI.loading);
    List<EmployeeDesignation> employeeDesignations = await _employeeDesignationRepository.getAllEmployeeDesignations();
    yield this.state.copyWith(employeeDesignations: employeeDesignations, employeeDesignationStatusUI: EmployeeDesignationStatusUI.done);
  }

  Stream<EmployeeDesignationState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        EmployeeDesignation result;
        if(this.state.editMode) {
          EmployeeDesignation newEmployeeDesignation = EmployeeDesignation(state.loadedEmployeeDesignation.id,
            this.state.designation.value,  
            this.state.description.value,  
            this.state.designationDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _employeeDesignationRepository.update(newEmployeeDesignation);
        } else {
          EmployeeDesignation newEmployeeDesignation = EmployeeDesignation(null,
            this.state.designation.value,  
            this.state.description.value,  
            this.state.designationDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _employeeDesignationRepository.create(newEmployeeDesignation);
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

  Stream<EmployeeDesignationState> onLoadEmployeeDesignationIdForEdit(LoadEmployeeDesignationByIdForEdit event) async* {
    yield this.state.copyWith(employeeDesignationStatusUI: EmployeeDesignationStatusUI.loading);
    EmployeeDesignation loadedEmployeeDesignation = await _employeeDesignationRepository.getEmployeeDesignation(event.id);

    final designation = DesignationInput.dirty(loadedEmployeeDesignation?.designation != null ? loadedEmployeeDesignation.designation: '');
    final description = DescriptionInput.dirty(loadedEmployeeDesignation?.description != null ? loadedEmployeeDesignation.description: '');
    final designationDate = DesignationDateInput.dirty(loadedEmployeeDesignation?.designationDate != null ? loadedEmployeeDesignation.designationDate: '');
    final clientId = ClientIdInput.dirty(loadedEmployeeDesignation?.clientId != null ? loadedEmployeeDesignation.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEmployeeDesignation?.hasExtraData != null ? loadedEmployeeDesignation.hasExtraData: false);

    yield this.state.copyWith(loadedEmployeeDesignation: loadedEmployeeDesignation, editMode: true,
      designation: designation,
      description: description,
      designationDate: designationDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    employeeDesignationStatusUI: EmployeeDesignationStatusUI.done);

    designationController.text = loadedEmployeeDesignation.designation;
    descriptionController.text = loadedEmployeeDesignation.description;
    designationDateController.text = loadedEmployeeDesignation.designationDate;
    clientIdController.text = loadedEmployeeDesignation.clientId.toString();
  }

  Stream<EmployeeDesignationState> onDeleteEmployeeDesignationId(DeleteEmployeeDesignationById event) async* {
    try {
      await _employeeDesignationRepository.delete(event.id);
      this.add(InitEmployeeDesignationList());
      yield this.state.copyWith(deleteStatus: EmployeeDesignationDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EmployeeDesignationDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EmployeeDesignationDeleteStatus.none);
  }

  Stream<EmployeeDesignationState> onLoadEmployeeDesignationIdForView(LoadEmployeeDesignationByIdForView event) async* {
    yield this.state.copyWith(employeeDesignationStatusUI: EmployeeDesignationStatusUI.loading);
    try {
      EmployeeDesignation loadedEmployeeDesignation = await _employeeDesignationRepository.getEmployeeDesignation(event.id);
      yield this.state.copyWith(loadedEmployeeDesignation: loadedEmployeeDesignation, employeeDesignationStatusUI: EmployeeDesignationStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEmployeeDesignation: null, employeeDesignationStatusUI: EmployeeDesignationStatusUI.error);
    }
  }


  Stream<EmployeeDesignationState> onDesignationChange(DesignationChanged event) async* {
    final designation = DesignationInput.dirty(event.designation);
    yield this.state.copyWith(
      designation: designation,
      formStatus: Formz.validate([
        designation,
      this.state.description,
      this.state.designationDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDesignationState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.designation,
        description,
      this.state.designationDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDesignationState> onDesignationDateChange(DesignationDateChanged event) async* {
    final designationDate = DesignationDateInput.dirty(event.designationDate);
    yield this.state.copyWith(
      designationDate: designationDate,
      formStatus: Formz.validate([
      this.state.designation,
      this.state.description,
        designationDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDesignationState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.designation,
      this.state.description,
      this.state.designationDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDesignationState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.designation,
      this.state.description,
      this.state.designationDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    designationController.dispose();
    descriptionController.dispose();
    designationDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}