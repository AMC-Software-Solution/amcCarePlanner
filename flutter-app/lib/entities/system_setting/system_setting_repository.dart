import 'package:amcCarePlanner/entities/system_setting/system_setting_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class SystemSettingRepository {
    SystemSettingRepository();
  
  static final String uriEndpoint = '/system-settings';

  Future<List<SystemSetting>> getAllSystemSettings() async {
    final allSystemSettingsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<SystemSetting>>(allSystemSettingsRequest.body);
  }

  Future<SystemSetting> getSystemSetting(int id) async {
    final systemSettingRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<SystemSetting>(systemSettingRequest.body);
  }

  Future<SystemSetting> create(SystemSetting systemSetting) async {
    final systemSettingRequest = await HttpUtils.postRequest('$uriEndpoint', systemSetting);
    return JsonMapper.deserialize<SystemSetting>(systemSettingRequest.body);
  }

  Future<SystemSetting> update(SystemSetting systemSetting) async {
    final systemSettingRequest = await HttpUtils.putRequest('$uriEndpoint', systemSetting);
    return JsonMapper.deserialize<SystemSetting>(systemSettingRequest.body);
  }

  Future<void> delete(int id) async {
    final systemSettingRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
