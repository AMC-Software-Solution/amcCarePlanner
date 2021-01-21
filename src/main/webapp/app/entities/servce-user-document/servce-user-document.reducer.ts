import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServceUserDocument, defaultValue } from 'app/shared/model/servce-user-document.model';

export const ACTION_TYPES = {
  FETCH_SERVCEUSERDOCUMENT_LIST: 'servceUserDocument/FETCH_SERVCEUSERDOCUMENT_LIST',
  FETCH_SERVCEUSERDOCUMENT: 'servceUserDocument/FETCH_SERVCEUSERDOCUMENT',
  CREATE_SERVCEUSERDOCUMENT: 'servceUserDocument/CREATE_SERVCEUSERDOCUMENT',
  UPDATE_SERVCEUSERDOCUMENT: 'servceUserDocument/UPDATE_SERVCEUSERDOCUMENT',
  DELETE_SERVCEUSERDOCUMENT: 'servceUserDocument/DELETE_SERVCEUSERDOCUMENT',
  SET_BLOB: 'servceUserDocument/SET_BLOB',
  RESET: 'servceUserDocument/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServceUserDocument>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ServceUserDocumentState = Readonly<typeof initialState>;

// Reducer

export default (state: ServceUserDocumentState = initialState, action): ServceUserDocumentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVCEUSERDOCUMENT):
    case REQUEST(ACTION_TYPES.UPDATE_SERVCEUSERDOCUMENT):
    case REQUEST(ACTION_TYPES.DELETE_SERVCEUSERDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT):
    case FAILURE(ACTION_TYPES.CREATE_SERVCEUSERDOCUMENT):
    case FAILURE(ACTION_TYPES.UPDATE_SERVCEUSERDOCUMENT):
    case FAILURE(ACTION_TYPES.DELETE_SERVCEUSERDOCUMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVCEUSERDOCUMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVCEUSERDOCUMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVCEUSERDOCUMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/servce-user-documents';

// Actions

export const getEntities: ICrudGetAllAction<IServceUserDocument> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT_LIST,
    payload: axios.get<IServceUserDocument>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IServceUserDocument> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVCEUSERDOCUMENT,
    payload: axios.get<IServceUserDocument>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServceUserDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVCEUSERDOCUMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServceUserDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVCEUSERDOCUMENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServceUserDocument> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVCEUSERDOCUMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
