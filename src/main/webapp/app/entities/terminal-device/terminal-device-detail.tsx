import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './terminal-device.reducer';
import { ITerminalDevice } from 'app/shared/model/terminal-device.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITerminalDeviceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TerminalDeviceDetail = (props: ITerminalDeviceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { terminalDeviceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.terminalDevice.detail.title">TerminalDevice</Translate> [<b>{terminalDeviceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="deviceName">
              <Translate contentKey="carePlannerApp.terminalDevice.deviceName">Device Name</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.deviceName}</dd>
          <dt>
            <span id="deviceModel">
              <Translate contentKey="carePlannerApp.terminalDevice.deviceModel">Device Model</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.deviceModel}</dd>
          <dt>
            <span id="registeredDate">
              <Translate contentKey="carePlannerApp.terminalDevice.registeredDate">Registered Date</Translate>
            </span>
          </dt>
          <dd>
            {terminalDeviceEntity.registeredDate ? (
              <TextFormat value={terminalDeviceEntity.registeredDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="imei">
              <Translate contentKey="carePlannerApp.terminalDevice.imei">Imei</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.imei}</dd>
          <dt>
            <span id="simNumber">
              <Translate contentKey="carePlannerApp.terminalDevice.simNumber">Sim Number</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.simNumber}</dd>
          <dt>
            <span id="userStartedUsingFrom">
              <Translate contentKey="carePlannerApp.terminalDevice.userStartedUsingFrom">User Started Using From</Translate>
            </span>
          </dt>
          <dd>
            {terminalDeviceEntity.userStartedUsingFrom ? (
              <TextFormat value={terminalDeviceEntity.userStartedUsingFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deviceOnLocationFrom">
              <Translate contentKey="carePlannerApp.terminalDevice.deviceOnLocationFrom">Device On Location From</Translate>
            </span>
          </dt>
          <dd>
            {terminalDeviceEntity.deviceOnLocationFrom ? (
              <TextFormat value={terminalDeviceEntity.deviceOnLocationFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="operatingSystem">
              <Translate contentKey="carePlannerApp.terminalDevice.operatingSystem">Operating System</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.operatingSystem}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.terminalDevice.note">Note</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.note}</dd>
          <dt>
            <span id="ownerEntityId">
              <Translate contentKey="carePlannerApp.terminalDevice.ownerEntityId">Owner Entity Id</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.ownerEntityId}</dd>
          <dt>
            <span id="ownerEntityName">
              <Translate contentKey="carePlannerApp.terminalDevice.ownerEntityName">Owner Entity Name</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.ownerEntityName}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.terminalDevice.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {terminalDeviceEntity.lastUpdatedDate ? (
              <TextFormat value={terminalDeviceEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.terminalDevice.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{terminalDeviceEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.terminalDevice.employee">Employee</Translate>
          </dt>
          <dd>{terminalDeviceEntity.employeeEmployeeCode ? terminalDeviceEntity.employeeEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/terminal-device" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/terminal-device/${terminalDeviceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ terminalDevice }: IRootState) => ({
  terminalDeviceEntity: terminalDevice.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TerminalDeviceDetail);
