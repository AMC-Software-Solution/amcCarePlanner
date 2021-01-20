import 'package:amcCarePlanner/entities/terminal_device/terminal_device_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class TerminalDeviceRepository {
    TerminalDeviceRepository();
  
  static final String uriEndpoint = '/terminal-devices';

  Future<List<TerminalDevice>> getAllTerminalDevices() async {
    final allTerminalDevicesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<TerminalDevice>>(allTerminalDevicesRequest.body);
  }

  Future<TerminalDevice> getTerminalDevice(int id) async {
    final terminalDeviceRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<TerminalDevice>(terminalDeviceRequest.body);
  }

  Future<TerminalDevice> create(TerminalDevice terminalDevice) async {
    final terminalDeviceRequest = await HttpUtils.postRequest('$uriEndpoint', terminalDevice);
    return JsonMapper.deserialize<TerminalDevice>(terminalDeviceRequest.body);
  }

  Future<TerminalDevice> update(TerminalDevice terminalDevice) async {
    final terminalDeviceRequest = await HttpUtils.putRequest('$uriEndpoint', terminalDevice);
    return JsonMapper.deserialize<TerminalDevice>(terminalDeviceRequest.body);
  }

  Future<void> delete(int id) async {
    final terminalDeviceRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
