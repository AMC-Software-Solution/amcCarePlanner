// ignore_for_file: directives_ordering

import 'package:build_runner_core/build_runner_core.dart' as _i1;
import 'package:dart_json_mapper/builder_factory.dart' as _i2;
import 'package:build_config/build_config.dart' as _i3;
import 'package:build/build.dart' as _i4;
import 'package:reflectable/reflectable_builder.dart' as _i5;
import 'dart:isolate' as _i6;
import 'package:build_runner/build_runner.dart' as _i7;
import 'dart:io' as _i8;

final _builders = <_i1.BuilderApplication>[
  _i1.apply('dart_json_mapper:dart_json_mapper', [_i2.dartJsonMapperBuilder],
      _i1.toRoot(),
      hideOutput: false,
      defaultGenerateFor: const _i3.InputSet(include: [
        'benchmark/**.dart',
        'bin/**.dart',
        'test/_*.dart',
        'example/**.dart',
        'lib/main.dart',
        'tool/**.dart',
        'web/**.dart'
      ], exclude: [
        'lib/**.dart'
      ]),
      defaultOptions: _i4.BuilderOptions({
        'iterables': 'List, Set',
        'extension': '.mapper.g.dart',
        'formatted': false
      })),
  _i1.apply('reflectable:reflectable', [_i5.reflectableBuilder], _i1.toRoot(),
      hideOutput: false,
      defaultGenerateFor: const _i3.InputSet(include: [
        'benchmark/**.dart',
        'bin/**.dart',
        'example/**.dart',
        'lib/main.dart',
        'test/**.dart',
        'tool/**.dart',
        'web/**.dart'
      ]))
];
void main(List<String> args, [_i6.SendPort sendPort]) async {
  var result = await _i7.run(args, _builders);
  sendPort?.send(result);
  _i8.exitCode = result;
}
