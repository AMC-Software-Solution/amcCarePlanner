import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './notification.reducer';
import { INotification } from 'app/shared/model/notification.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INotificationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NotificationDetail = (props: INotificationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { notificationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.notification.detail.title">Notification</Translate> [<b>{notificationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="carePlannerApp.notification.title">Title</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.title}</dd>
          <dt>
            <span id="body">
              <Translate contentKey="carePlannerApp.notification.body">Body</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.body}</dd>
          <dt>
            <span id="notificationDate">
              <Translate contentKey="carePlannerApp.notification.notificationDate">Notification Date</Translate>
            </span>
          </dt>
          <dd>
            {notificationEntity.notificationDate ? (
              <TextFormat value={notificationEntity.notificationDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="image">
              <Translate contentKey="carePlannerApp.notification.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {notificationEntity.image ? (
              <div>
                {notificationEntity.imageContentType ? (
                  <a onClick={openFile(notificationEntity.imageContentType, notificationEntity.image)}>
                    <img
                      src={`data:${notificationEntity.imageContentType};base64,${notificationEntity.image}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {notificationEntity.imageContentType}, {byteSize(notificationEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="carePlannerApp.notification.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.imageUrl}</dd>
          <dt>
            <span id="senderId">
              <Translate contentKey="carePlannerApp.notification.senderId">Sender Id</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.senderId}</dd>
          <dt>
            <span id="receiverId">
              <Translate contentKey="carePlannerApp.notification.receiverId">Receiver Id</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.receiverId}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.notification.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {notificationEntity.createdDate ? (
              <TextFormat value={notificationEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.notification.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {notificationEntity.lastUpdatedDate ? (
              <TextFormat value={notificationEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.notification.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.notification.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/notification" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification/${notificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ notification }: IRootState) => ({
  notificationEntity: notification.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NotificationDetail);
