import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICarerServiceUserRelation, defaultValue } from 'app/shared/model/carer-service-user-relation.model';

export const ACTION_TYPES = {
  FETCH_CARERSERVICEUSERRELATION_LIST: 'carerServiceUserRelation/FETCH_CARERSERVICEUSERRELATION_LIST',
  FETCH_CARERSERVICEUSERRELATION: 'carerServiceUserRelation/FETCH_CARERSERVICEUSERRELATION',
  CREATE_CARERSERVICEUSERRELATION: 'carerServiceUserRelation/CREATE_CARERSERVICEUSERRELATION',
  UPDATE_CARERSERVICEUSERRELATION: 'carerServiceUserRelation/UPDATE_CARERSERVICEUSERRELATION',
  DELETE_CARERSERVICEUSERRELATION: 'carerServiceUserRelation/DELETE_CARERSERVICEUSERRELATION',
  RESET: 'carerServiceUserRelation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICarerServiceUserRelation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CarerServiceUserRelationState = Readonly<typeof initialState>;

// Reducer

export default (state: CarerServiceUserRelationState = initialState, action): CarerServiceUserRelationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CARERSERVICEUSERRELATION):
    case REQUEST(ACTION_TYPES.UPDATE_CARERSERVICEUSERRELATION):
    case REQUEST(ACTION_TYPES.DELETE_CARERSERVICEUSERRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION):
    case FAILURE(ACTION_TYPES.CREATE_CARERSERVICEUSERRELATION):
    case FAILURE(ACTION_TYPES.UPDATE_CARERSERVICEUSERRELATION):
    case FAILURE(ACTION_TYPES.DELETE_CARERSERVICEUSERRELATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CARERSERVICEUSERRELATION):
    case SUCCESS(ACTION_TYPES.UPDATE_CARERSERVICEUSERRELATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CARERSERVICEUSERRELATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/carer-service-user-relations';

// Actions

export const getEntities: ICrudGetAllAction<ICarerServiceUserRelation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION_LIST,
    payload: axios.get<ICarerServiceUserRelation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICarerServiceUserRelation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CARERSERVICEUSERRELATION,
    payload: axios.get<ICarerServiceUserRelation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICarerServiceUserRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CARERSERVICEUSERRELATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICarerServiceUserRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CARERSERVICEUSERRELATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICarerServiceUserRelation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CARERSERVICEUSERRELATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
