import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/employee_document/employee_document_model.dart';
import 'package:amcCarePlanner/entities/employee_document/employee_document_repository.dart';
import 'package:amcCarePlanner/entities/employee_document/bloc/employee_document_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'employee_document_events.dart';
part 'employee_document_state.dart';

class EmployeeDocumentBloc extends Bloc<EmployeeDocumentEvent, EmployeeDocumentState> {
  final EmployeeDocumentRepository _employeeDocumentRepository;

  final documentNameController = TextEditingController();
  final documentNumberController = TextEditingController();
  final noteController = TextEditingController();
  final issuedDateController = TextEditingController();
  final expiryDateController = TextEditingController();
  final uploadedDateController = TextEditingController();
  final documentFileUrlController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EmployeeDocumentBloc({@required EmployeeDocumentRepository employeeDocumentRepository}) : assert(employeeDocumentRepository != null),
        _employeeDocumentRepository = employeeDocumentRepository, 
  super(EmployeeDocumentState(null,null,null,null,null,));

  @override
  void onTransition(Transition<EmployeeDocumentEvent, EmployeeDocumentState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EmployeeDocumentState> mapEventToState(EmployeeDocumentEvent event) async* {
    if (event is InitEmployeeDocumentList) {
      yield* onInitList(event);
    } else if (event is EmployeeDocumentFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEmployeeDocumentByIdForEdit) {
      yield* onLoadEmployeeDocumentIdForEdit(event);
    } else if (event is DeleteEmployeeDocumentById) {
      yield* onDeleteEmployeeDocumentId(event);
    } else if (event is LoadEmployeeDocumentByIdForView) {
      yield* onLoadEmployeeDocumentIdForView(event);
    }else if (event is DocumentNameChanged){
      yield* onDocumentNameChange(event);
    }else if (event is DocumentNumberChanged){
      yield* onDocumentNumberChange(event);
    }else if (event is DocumentStatusChanged){
      yield* onDocumentStatusChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is IssuedDateChanged){
      yield* onIssuedDateChange(event);
    }else if (event is ExpiryDateChanged){
      yield* onExpiryDateChange(event);
    }else if (event is UploadedDateChanged){
      yield* onUploadedDateChange(event);
    }else if (event is DocumentFileUrlChanged){
      yield* onDocumentFileUrlChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<EmployeeDocumentState> onInitList(InitEmployeeDocumentList event) async* {
    yield this.state.copyWith(employeeDocumentStatusUI: EmployeeDocumentStatusUI.loading);
    List<EmployeeDocument> employeeDocuments = await _employeeDocumentRepository.getAllEmployeeDocuments();
    yield this.state.copyWith(employeeDocuments: employeeDocuments, employeeDocumentStatusUI: EmployeeDocumentStatusUI.done);
  }

  Stream<EmployeeDocumentState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        EmployeeDocument result;
        if(this.state.editMode) {
          EmployeeDocument newEmployeeDocument = EmployeeDocument(state.loadedEmployeeDocument.id,
            this.state.documentName.value,  
            this.state.documentNumber.value,  
            this.state.documentStatus.value,  
            this.state.note.value,  
            this.state.issuedDate.value,  
            this.state.expiryDate.value,  
            this.state.uploadedDate.value,  
            this.state.documentFileUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _employeeDocumentRepository.update(newEmployeeDocument);
        } else {
          EmployeeDocument newEmployeeDocument = EmployeeDocument(null,
            this.state.documentName.value,  
            this.state.documentNumber.value,  
            this.state.documentStatus.value,  
            this.state.note.value,  
            this.state.issuedDate.value,  
            this.state.expiryDate.value,  
            this.state.uploadedDate.value,  
            this.state.documentFileUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _employeeDocumentRepository.create(newEmployeeDocument);
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

  Stream<EmployeeDocumentState> onLoadEmployeeDocumentIdForEdit(LoadEmployeeDocumentByIdForEdit event) async* {
    yield this.state.copyWith(employeeDocumentStatusUI: EmployeeDocumentStatusUI.loading);
    EmployeeDocument loadedEmployeeDocument = await _employeeDocumentRepository.getEmployeeDocument(event.id);

    final documentName = DocumentNameInput.dirty(loadedEmployeeDocument?.documentName != null ? loadedEmployeeDocument.documentName: '');
    final documentNumber = DocumentNumberInput.dirty(loadedEmployeeDocument?.documentNumber != null ? loadedEmployeeDocument.documentNumber: '');
    final documentStatus = DocumentStatusInput.dirty(loadedEmployeeDocument?.documentStatus != null ? loadedEmployeeDocument.documentStatus: null);
    final note = NoteInput.dirty(loadedEmployeeDocument?.note != null ? loadedEmployeeDocument.note: '');
    final issuedDate = IssuedDateInput.dirty(loadedEmployeeDocument?.issuedDate != null ? loadedEmployeeDocument.issuedDate: null);
    final expiryDate = ExpiryDateInput.dirty(loadedEmployeeDocument?.expiryDate != null ? loadedEmployeeDocument.expiryDate: null);
    final uploadedDate = UploadedDateInput.dirty(loadedEmployeeDocument?.uploadedDate != null ? loadedEmployeeDocument.uploadedDate: null);
    final documentFileUrl = DocumentFileUrlInput.dirty(loadedEmployeeDocument?.documentFileUrl != null ? loadedEmployeeDocument.documentFileUrl: '');
    final createdDate = CreatedDateInput.dirty(loadedEmployeeDocument?.createdDate != null ? loadedEmployeeDocument.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEmployeeDocument?.lastUpdatedDate != null ? loadedEmployeeDocument.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEmployeeDocument?.clientId != null ? loadedEmployeeDocument.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEmployeeDocument?.hasExtraData != null ? loadedEmployeeDocument.hasExtraData: false);

    yield this.state.copyWith(loadedEmployeeDocument: loadedEmployeeDocument, editMode: true,
      documentName: documentName,
      documentNumber: documentNumber,
      documentStatus: documentStatus,
      note: note,
      issuedDate: issuedDate,
      expiryDate: expiryDate,
      uploadedDate: uploadedDate,
      documentFileUrl: documentFileUrl,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    employeeDocumentStatusUI: EmployeeDocumentStatusUI.done);

    documentNameController.text = loadedEmployeeDocument.documentName;
    documentNumberController.text = loadedEmployeeDocument.documentNumber;
    noteController.text = loadedEmployeeDocument.note;
    issuedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeDocument?.issuedDate);
    expiryDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeDocument?.expiryDate);
    uploadedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeDocument?.uploadedDate);
    documentFileUrlController.text = loadedEmployeeDocument.documentFileUrl;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeDocument?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeDocument?.lastUpdatedDate);
    clientIdController.text = loadedEmployeeDocument.clientId.toString();
  }

  Stream<EmployeeDocumentState> onDeleteEmployeeDocumentId(DeleteEmployeeDocumentById event) async* {
    try {
      await _employeeDocumentRepository.delete(event.id);
      this.add(InitEmployeeDocumentList());
      yield this.state.copyWith(deleteStatus: EmployeeDocumentDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EmployeeDocumentDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EmployeeDocumentDeleteStatus.none);
  }

  Stream<EmployeeDocumentState> onLoadEmployeeDocumentIdForView(LoadEmployeeDocumentByIdForView event) async* {
    yield this.state.copyWith(employeeDocumentStatusUI: EmployeeDocumentStatusUI.loading);
    try {
      EmployeeDocument loadedEmployeeDocument = await _employeeDocumentRepository.getEmployeeDocument(event.id);
      yield this.state.copyWith(loadedEmployeeDocument: loadedEmployeeDocument, employeeDocumentStatusUI: EmployeeDocumentStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEmployeeDocument: null, employeeDocumentStatusUI: EmployeeDocumentStatusUI.error);
    }
  }


  Stream<EmployeeDocumentState> onDocumentNameChange(DocumentNameChanged event) async* {
    final documentName = DocumentNameInput.dirty(event.documentName);
    yield this.state.copyWith(
      documentName: documentName,
      formStatus: Formz.validate([
        documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onDocumentNumberChange(DocumentNumberChanged event) async* {
    final documentNumber = DocumentNumberInput.dirty(event.documentNumber);
    yield this.state.copyWith(
      documentNumber: documentNumber,
      formStatus: Formz.validate([
      this.state.documentName,
        documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onDocumentStatusChange(DocumentStatusChanged event) async* {
    final documentStatus = DocumentStatusInput.dirty(event.documentStatus);
    yield this.state.copyWith(
      documentStatus: documentStatus,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
        documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
        note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onIssuedDateChange(IssuedDateChanged event) async* {
    final issuedDate = IssuedDateInput.dirty(event.issuedDate);
    yield this.state.copyWith(
      issuedDate: issuedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
        issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onExpiryDateChange(ExpiryDateChanged event) async* {
    final expiryDate = ExpiryDateInput.dirty(event.expiryDate);
    yield this.state.copyWith(
      expiryDate: expiryDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
        expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onUploadedDateChange(UploadedDateChanged event) async* {
    final uploadedDate = UploadedDateInput.dirty(event.uploadedDate);
    yield this.state.copyWith(
      uploadedDate: uploadedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
        uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onDocumentFileUrlChange(DocumentFileUrlChanged event) async* {
    final documentFileUrl = DocumentFileUrlInput.dirty(event.documentFileUrl);
    yield this.state.copyWith(
      documentFileUrl: documentFileUrl,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
        documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeDocumentState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    documentNameController.dispose();
    documentNumberController.dispose();
    noteController.dispose();
    issuedDateController.dispose();
    expiryDateController.dispose();
    uploadedDateController.dispose();
    documentFileUrlController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}