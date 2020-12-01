import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './system-events-history.reducer';
import { ISystemEventsHistory } from 'app/shared/model/system-events-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ISystemEventsHistoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SystemEventsHistory = (props: ISystemEventsHistoryProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { systemEventsHistoryList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="system-events-history-heading">
        <Translate contentKey="carePlannerApp.systemEventsHistory.home.title">System Events Histories</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.systemEventsHistory.home.createLabel">Create new System Events History</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {systemEventsHistoryList && systemEventsHistoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventName')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventName">Event Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventDate')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventDate">Event Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventApi')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventApi">Event Api</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ipAddress')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.ipAddress">Ip Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventNote')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventNote">Event Note</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventEntityName')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventEntityName">Event Entity Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventEntityId')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventEntityId">Event Entity Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isSuspecious')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.isSuspecious">Is Suspecious</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.systemEventsHistory.triggedBy">Trigged By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {systemEventsHistoryList.map((systemEventsHistory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${systemEventsHistory.id}`} color="link" size="sm">
                      {systemEventsHistory.id}
                    </Button>
                  </td>
                  <td>{systemEventsHistory.eventName}</td>
                  <td>
                    {systemEventsHistory.eventDate ? (
                      <TextFormat type="date" value={systemEventsHistory.eventDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{systemEventsHistory.eventApi}</td>
                  <td>{systemEventsHistory.ipAddress}</td>
                  <td>{systemEventsHistory.eventNote}</td>
                  <td>{systemEventsHistory.eventEntityName}</td>
                  <td>{systemEventsHistory.eventEntityId}</td>
                  <td>{systemEventsHistory.isSuspecious ? 'true' : 'false'}</td>
                  <td>{systemEventsHistory.clientId}</td>
                  <td>{systemEventsHistory.triggedByLogin ? systemEventsHistory.triggedByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${systemEventsHistory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${systemEventsHistory.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${systemEventsHistory.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.systemEventsHistory.home.notFound">No System Events Histories found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={systemEventsHistoryList && systemEventsHistoryList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ systemEventsHistory }: IRootState) => ({
  systemEventsHistoryList: systemEventsHistory.entities,
  loading: systemEventsHistory.loading,
  totalItems: systemEventsHistory.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemEventsHistory);
