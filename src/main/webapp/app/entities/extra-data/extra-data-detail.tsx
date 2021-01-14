import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './extra-data.reducer';
import { IExtraData } from 'app/shared/model/extra-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExtraDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExtraDataDetail = (props: IExtraDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { extraDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.extraData.detail.title">ExtraData</Translate> [<b>{extraDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="entityName">
              <Translate contentKey="carePlannerApp.extraData.entityName">Entity Name</Translate>
            </span>
          </dt>
          <dd>{extraDataEntity.entityName}</dd>
          <dt>
            <span id="entityId">
              <Translate contentKey="carePlannerApp.extraData.entityId">Entity Id</Translate>
            </span>
          </dt>
          <dd>{extraDataEntity.entityId}</dd>
          <dt>
            <span id="extraDataKey">
              <Translate contentKey="carePlannerApp.extraData.extraDataKey">Extra Data Key</Translate>
            </span>
          </dt>
          <dd>{extraDataEntity.extraDataKey}</dd>
          <dt>
            <span id="extraDataValue">
              <Translate contentKey="carePlannerApp.extraData.extraDataValue">Extra Data Value</Translate>
            </span>
          </dt>
          <dd>{extraDataEntity.extraDataValue}</dd>
          <dt>
            <span id="extraDataValueDataType">
              <Translate contentKey="carePlannerApp.extraData.extraDataValueDataType">Extra Data Value Data Type</Translate>
            </span>
          </dt>
          <dd>{extraDataEntity.extraDataValueDataType}</dd>
          <dt>
            <span id="extraDataDescription">
              <Translate contentKey="carePlannerApp.extraData.extraDataDescription">Extra Data Description</Translate>
            </span>
          </dt>
          <dd>{extraDataEntity.extraDataDescription}</dd>
          <dt>
            <span id="extraDataDate">
              <Translate contentKey="carePlannerApp.extraData.extraDataDate">Extra Data Date</Translate>
            </span>
          </dt>
          <dd>
            {extraDataEntity.extraDataDate ? (
              <TextFormat value={extraDataEntity.extraDataDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.extraData.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{extraDataEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/extra-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/extra-data/${extraDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ extraData }: IRootState) => ({
  extraDataEntity: extraData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExtraDataDetail);
