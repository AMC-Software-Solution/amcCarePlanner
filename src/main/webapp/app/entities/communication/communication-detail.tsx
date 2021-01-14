import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './communication.reducer';
import { ICommunication } from 'app/shared/model/communication.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICommunicationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommunicationDetail = (props: ICommunicationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { communicationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.communication.detail.title">Communication</Translate> [<b>{communicationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="communicationType">
              <Translate contentKey="carePlannerApp.communication.communicationType">Communication Type</Translate>
            </span>
          </dt>
          <dd>{communicationEntity.communicationType}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.communication.note">Note</Translate>
            </span>
          </dt>
          <dd>{communicationEntity.note}</dd>
          <dt>
            <span id="communicationDate">
              <Translate contentKey="carePlannerApp.communication.communicationDate">Communication Date</Translate>
            </span>
          </dt>
          <dd>
            {communicationEntity.communicationDate ? (
              <TextFormat value={communicationEntity.communicationDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attachment">
              <Translate contentKey="carePlannerApp.communication.attachment">Attachment</Translate>
            </span>
          </dt>
          <dd>
            {communicationEntity.attachment ? (
              <div>
                {communicationEntity.attachmentContentType ? (
                  <a onClick={openFile(communicationEntity.attachmentContentType, communicationEntity.attachment)}>
                    <img
                      src={`data:${communicationEntity.attachmentContentType};base64,${communicationEntity.attachment}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {communicationEntity.attachmentContentType}, {byteSize(communicationEntity.attachment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="attachmentUrl">
              <Translate contentKey="carePlannerApp.communication.attachmentUrl">Attachment Url</Translate>
            </span>
          </dt>
          <dd>{communicationEntity.attachmentUrl}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.communication.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {communicationEntity.createdDate ? (
              <TextFormat value={communicationEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.communication.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {communicationEntity.lastUpdatedDate ? (
              <TextFormat value={communicationEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.communication.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{communicationEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.communication.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{communicationEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.communication.serviceUser">Service User</Translate>
          </dt>
          <dd>{communicationEntity.serviceUserServiceUserCode ? communicationEntity.serviceUserServiceUserCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.communication.communicatedBy">Communicated By</Translate>
          </dt>
          <dd>{communicationEntity.communicatedByEmployeeCode ? communicationEntity.communicatedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/communication" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/communication/${communicationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ communication }: IRootState) => ({
  communicationEntity: communication.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommunicationDetail);
