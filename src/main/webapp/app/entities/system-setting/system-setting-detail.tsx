import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './system-setting.reducer';
import { ISystemSetting } from 'app/shared/model/system-setting.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISystemSettingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemSettingDetail = (props: ISystemSettingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { systemSettingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.systemSetting.detail.title">SystemSetting</Translate> [<b>{systemSettingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fieldName">
              <Translate contentKey="carePlannerApp.systemSetting.fieldName">Field Name</Translate>
            </span>
          </dt>
          <dd>{systemSettingEntity.fieldName}</dd>
          <dt>
            <span id="fieldValue">
              <Translate contentKey="carePlannerApp.systemSetting.fieldValue">Field Value</Translate>
            </span>
          </dt>
          <dd>{systemSettingEntity.fieldValue}</dd>
          <dt>
            <span id="defaultValue">
              <Translate contentKey="carePlannerApp.systemSetting.defaultValue">Default Value</Translate>
            </span>
          </dt>
          <dd>{systemSettingEntity.defaultValue}</dd>
          <dt>
            <span id="settingEnabled">
              <Translate contentKey="carePlannerApp.systemSetting.settingEnabled">Setting Enabled</Translate>
            </span>
          </dt>
          <dd>{systemSettingEntity.settingEnabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.systemSetting.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {systemSettingEntity.createdDate ? (
              <TextFormat value={systemSettingEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="carePlannerApp.systemSetting.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {systemSettingEntity.updatedDate ? (
              <TextFormat value={systemSettingEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.systemSetting.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{systemSettingEntity.clientId}</dd>
        </dl>
        <Button tag={Link} to="/system-setting" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/system-setting/${systemSettingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ systemSetting }: IRootState) => ({
  systemSettingEntity: systemSetting.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemSettingDetail);
