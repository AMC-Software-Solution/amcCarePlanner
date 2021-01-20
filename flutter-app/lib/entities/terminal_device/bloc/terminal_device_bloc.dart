import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/terminal_device/terminal_device_model.dart';
import 'package:amcCarePlanner/entities/terminal_device/terminal_device_repository.dart';
import 'package:amcCarePlanner/entities/terminal_device/bloc/terminal_device_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'terminal_device_events.dart';
part 'terminal_device_state.dart';

class TerminalDeviceBloc extends Bloc<TerminalDeviceEvent, TerminalDeviceState> {
  final TerminalDeviceRepository _terminalDeviceRepository;

  final deviceNameController = TextEditingController();
  final deviceModelController = TextEditingController();
  final registeredDateController = TextEditingController();
  final imeiController = TextEditingController();
  final simNumberController = TextEditingController();
  final userStartedUsingFromController = TextEditingController();
  final deviceOnLocationFromController = TextEditingController();
  final operatingSystemController = TextEditingController();
  final noteController = TextEditingController();
  final ownerEntityIdController = TextEditingController();
  final ownerEntityNameController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  TerminalDeviceBloc({@required TerminalDeviceRepository terminalDeviceRepository}) : assert(terminalDeviceRepository != null),
        _terminalDeviceRepository = terminalDeviceRepository, 
  super(TerminalDeviceState(null,null,null,null,null,));

  @override
  void onTransition(Transition<TerminalDeviceEvent, TerminalDeviceState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<TerminalDeviceState> mapEventToState(TerminalDeviceEvent event) async* {
    if (event is InitTerminalDeviceList) {
      yield* onInitList(event);
    } else if (event is TerminalDeviceFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadTerminalDeviceByIdForEdit) {
      yield* onLoadTerminalDeviceIdForEdit(event);
    } else if (event is DeleteTerminalDeviceById) {
      yield* onDeleteTerminalDeviceId(event);
    } else if (event is LoadTerminalDeviceByIdForView) {
      yield* onLoadTerminalDeviceIdForView(event);
    }else if (event is DeviceNameChanged){
      yield* onDeviceNameChange(event);
    }else if (event is DeviceModelChanged){
      yield* onDeviceModelChange(event);
    }else if (event is RegisteredDateChanged){
      yield* onRegisteredDateChange(event);
    }else if (event is ImeiChanged){
      yield* onImeiChange(event);
    }else if (event is SimNumberChanged){
      yield* onSimNumberChange(event);
    }else if (event is UserStartedUsingFromChanged){
      yield* onUserStartedUsingFromChange(event);
    }else if (event is DeviceOnLocationFromChanged){
      yield* onDeviceOnLocationFromChange(event);
    }else if (event is OperatingSystemChanged){
      yield* onOperatingSystemChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is OwnerEntityIdChanged){
      yield* onOwnerEntityIdChange(event);
    }else if (event is OwnerEntityNameChanged){
      yield* onOwnerEntityNameChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<TerminalDeviceState> onInitList(InitTerminalDeviceList event) async* {
    yield this.state.copyWith(terminalDeviceStatusUI: TerminalDeviceStatusUI.loading);
    List<TerminalDevice> terminalDevices = await _terminalDeviceRepository.getAllTerminalDevices();
    yield this.state.copyWith(terminalDevices: terminalDevices, terminalDeviceStatusUI: TerminalDeviceStatusUI.done);
  }

  Stream<TerminalDeviceState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        TerminalDevice result;
        if(this.state.editMode) {
          TerminalDevice newTerminalDevice = TerminalDevice(state.loadedTerminalDevice.id,
            this.state.deviceName.value,  
            this.state.deviceModel.value,  
            this.state.registeredDate.value,  
            this.state.imei.value,  
            this.state.simNumber.value,  
            this.state.userStartedUsingFrom.value,  
            this.state.deviceOnLocationFrom.value,  
            this.state.operatingSystem.value,  
            this.state.note.value,  
            this.state.ownerEntityId.value,  
            this.state.ownerEntityName.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _terminalDeviceRepository.update(newTerminalDevice);
        } else {
          TerminalDevice newTerminalDevice = TerminalDevice(null,
            this.state.deviceName.value,  
            this.state.deviceModel.value,  
            this.state.registeredDate.value,  
            this.state.imei.value,  
            this.state.simNumber.value,  
            this.state.userStartedUsingFrom.value,  
            this.state.deviceOnLocationFrom.value,  
            this.state.operatingSystem.value,  
            this.state.note.value,  
            this.state.ownerEntityId.value,  
            this.state.ownerEntityName.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _terminalDeviceRepository.create(newTerminalDevice);
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

  Stream<TerminalDeviceState> onLoadTerminalDeviceIdForEdit(LoadTerminalDeviceByIdForEdit event) async* {
    yield this.state.copyWith(terminalDeviceStatusUI: TerminalDeviceStatusUI.loading);
    TerminalDevice loadedTerminalDevice = await _terminalDeviceRepository.getTerminalDevice(event.id);

    final deviceName = DeviceNameInput.dirty(loadedTerminalDevice?.deviceName != null ? loadedTerminalDevice.deviceName: '');
    final deviceModel = DeviceModelInput.dirty(loadedTerminalDevice?.deviceModel != null ? loadedTerminalDevice.deviceModel: '');
    final registeredDate = RegisteredDateInput.dirty(loadedTerminalDevice?.registeredDate != null ? loadedTerminalDevice.registeredDate: null);
    final imei = ImeiInput.dirty(loadedTerminalDevice?.imei != null ? loadedTerminalDevice.imei: '');
    final simNumber = SimNumberInput.dirty(loadedTerminalDevice?.simNumber != null ? loadedTerminalDevice.simNumber: '');
    final userStartedUsingFrom = UserStartedUsingFromInput.dirty(loadedTerminalDevice?.userStartedUsingFrom != null ? loadedTerminalDevice.userStartedUsingFrom: null);
    final deviceOnLocationFrom = DeviceOnLocationFromInput.dirty(loadedTerminalDevice?.deviceOnLocationFrom != null ? loadedTerminalDevice.deviceOnLocationFrom: null);
    final operatingSystem = OperatingSystemInput.dirty(loadedTerminalDevice?.operatingSystem != null ? loadedTerminalDevice.operatingSystem: '');
    final note = NoteInput.dirty(loadedTerminalDevice?.note != null ? loadedTerminalDevice.note: '');
    final ownerEntityId = OwnerEntityIdInput.dirty(loadedTerminalDevice?.ownerEntityId != null ? loadedTerminalDevice.ownerEntityId: 0);
    final ownerEntityName = OwnerEntityNameInput.dirty(loadedTerminalDevice?.ownerEntityName != null ? loadedTerminalDevice.ownerEntityName: '');
    final createdDate = CreatedDateInput.dirty(loadedTerminalDevice?.createdDate != null ? loadedTerminalDevice.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedTerminalDevice?.lastUpdatedDate != null ? loadedTerminalDevice.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedTerminalDevice?.clientId != null ? loadedTerminalDevice.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedTerminalDevice?.hasExtraData != null ? loadedTerminalDevice.hasExtraData: false);

    yield this.state.copyWith(loadedTerminalDevice: loadedTerminalDevice, editMode: true,
      deviceName: deviceName,
      deviceModel: deviceModel,
      registeredDate: registeredDate,
      imei: imei,
      simNumber: simNumber,
      userStartedUsingFrom: userStartedUsingFrom,
      deviceOnLocationFrom: deviceOnLocationFrom,
      operatingSystem: operatingSystem,
      note: note,
      ownerEntityId: ownerEntityId,
      ownerEntityName: ownerEntityName,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    terminalDeviceStatusUI: TerminalDeviceStatusUI.done);

    deviceNameController.text = loadedTerminalDevice.deviceName;
    deviceModelController.text = loadedTerminalDevice.deviceModel;
    registeredDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTerminalDevice?.registeredDate);
    imeiController.text = loadedTerminalDevice.imei;
    simNumberController.text = loadedTerminalDevice.simNumber;
    userStartedUsingFromController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTerminalDevice?.userStartedUsingFrom);
    deviceOnLocationFromController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTerminalDevice?.deviceOnLocationFrom);
    operatingSystemController.text = loadedTerminalDevice.operatingSystem;
    noteController.text = loadedTerminalDevice.note;
    ownerEntityIdController.text = loadedTerminalDevice.ownerEntityId.toString();
    ownerEntityNameController.text = loadedTerminalDevice.ownerEntityName;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTerminalDevice?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTerminalDevice?.lastUpdatedDate);
    clientIdController.text = loadedTerminalDevice.clientId.toString();
  }

  Stream<TerminalDeviceState> onDeleteTerminalDeviceId(DeleteTerminalDeviceById event) async* {
    try {
      await _terminalDeviceRepository.delete(event.id);
      this.add(InitTerminalDeviceList());
      yield this.state.copyWith(deleteStatus: TerminalDeviceDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: TerminalDeviceDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: TerminalDeviceDeleteStatus.none);
  }

  Stream<TerminalDeviceState> onLoadTerminalDeviceIdForView(LoadTerminalDeviceByIdForView event) async* {
    yield this.state.copyWith(terminalDeviceStatusUI: TerminalDeviceStatusUI.loading);
    try {
      TerminalDevice loadedTerminalDevice = await _terminalDeviceRepository.getTerminalDevice(event.id);
      yield this.state.copyWith(loadedTerminalDevice: loadedTerminalDevice, terminalDeviceStatusUI: TerminalDeviceStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedTerminalDevice: null, terminalDeviceStatusUI: TerminalDeviceStatusUI.error);
    }
  }


  Stream<TerminalDeviceState> onDeviceNameChange(DeviceNameChanged event) async* {
    final deviceName = DeviceNameInput.dirty(event.deviceName);
    yield this.state.copyWith(
      deviceName: deviceName,
      formStatus: Formz.validate([
        deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onDeviceModelChange(DeviceModelChanged event) async* {
    final deviceModel = DeviceModelInput.dirty(event.deviceModel);
    yield this.state.copyWith(
      deviceModel: deviceModel,
      formStatus: Formz.validate([
      this.state.deviceName,
        deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onRegisteredDateChange(RegisteredDateChanged event) async* {
    final registeredDate = RegisteredDateInput.dirty(event.registeredDate);
    yield this.state.copyWith(
      registeredDate: registeredDate,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
        registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onImeiChange(ImeiChanged event) async* {
    final imei = ImeiInput.dirty(event.imei);
    yield this.state.copyWith(
      imei: imei,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
        imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onSimNumberChange(SimNumberChanged event) async* {
    final simNumber = SimNumberInput.dirty(event.simNumber);
    yield this.state.copyWith(
      simNumber: simNumber,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
        simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onUserStartedUsingFromChange(UserStartedUsingFromChanged event) async* {
    final userStartedUsingFrom = UserStartedUsingFromInput.dirty(event.userStartedUsingFrom);
    yield this.state.copyWith(
      userStartedUsingFrom: userStartedUsingFrom,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
        userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onDeviceOnLocationFromChange(DeviceOnLocationFromChanged event) async* {
    final deviceOnLocationFrom = DeviceOnLocationFromInput.dirty(event.deviceOnLocationFrom);
    yield this.state.copyWith(
      deviceOnLocationFrom: deviceOnLocationFrom,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
        deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onOperatingSystemChange(OperatingSystemChanged event) async* {
    final operatingSystem = OperatingSystemInput.dirty(event.operatingSystem);
    yield this.state.copyWith(
      operatingSystem: operatingSystem,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
        operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
        note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onOwnerEntityIdChange(OwnerEntityIdChanged event) async* {
    final ownerEntityId = OwnerEntityIdInput.dirty(event.ownerEntityId);
    yield this.state.copyWith(
      ownerEntityId: ownerEntityId,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
        ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onOwnerEntityNameChange(OwnerEntityNameChanged event) async* {
    final ownerEntityName = OwnerEntityNameInput.dirty(event.ownerEntityName);
    yield this.state.copyWith(
      ownerEntityName: ownerEntityName,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
        ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TerminalDeviceState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.deviceName,
      this.state.deviceModel,
      this.state.registeredDate,
      this.state.imei,
      this.state.simNumber,
      this.state.userStartedUsingFrom,
      this.state.deviceOnLocationFrom,
      this.state.operatingSystem,
      this.state.note,
      this.state.ownerEntityId,
      this.state.ownerEntityName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    deviceNameController.dispose();
    deviceModelController.dispose();
    registeredDateController.dispose();
    imeiController.dispose();
    simNumberController.dispose();
    userStartedUsingFromController.dispose();
    deviceOnLocationFromController.dispose();
    operatingSystemController.dispose();
    noteController.dispose();
    ownerEntityIdController.dispose();
    ownerEntityNameController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}