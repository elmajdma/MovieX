package elmajdma.movie.data.datamanager;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by majd on 02-Mar-18.
 */

public abstract class NetworkBoundSource <LocalType, RemoteType> {
    private Observable<Resource<LocalType>> result ;

    public NetworkBoundSource(ObservableEmitter<Resource<LocalType>> emitter) {

    Disposable firstDataDisposable = getLocal()
            .map(Resource:: loading)
            .subscribe(emitter::onNext);

    getRemote().map(mapper())
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe(localTypeData -> {
        firstDataDisposable.dispose();
        saveCallResult(localTypeData);
        getLocal().map(Resource::success).subscribe(emitter::onNext);
    });
}
    public abstract Observable<RemoteType> getRemote();

    public abstract Flowable<LocalType> getLocal();

    public abstract void saveCallResult(LocalType data);

    public abstract Function<RemoteType, LocalType> mapper();


}
