Yes, you can! Using the `.stage` operator is a brilliant way to keep your Mutiny pipeline reading as a clean, top-to-bottom fluent chain—bringing it much closer to that elegant RxJS syntax you like.

The `.stage` operator in Mutiny allows you to step out of the standard fluent chain, pass the entire upstream `Multi` into a custom transformation function, and return a new `Multi`. It is essentially a custom operator plugin mechanism.

---

### The Clean Fluent Refactor using `.stage`

By extracting the zipping logic into a reusable extension function or lambda, you can attach your pacing mechanism cleanly using `.stage`:

```kotlin
import io.smallrye.mutiny.Multi
import java.time.Duration

// 1. Define a reusable pacing function
fun <T> spaceOut(interval: Duration): (Multi<T>) -> Multi<T> {
    return { upstreamMulti ->
        val ticks = Multi.createFrom().ticks().every(interval)
        Multi.createBy().zipping(upstreamMulti, ticks) { item, _ -> item }
    }
}

// 2. Use it inline in your pipeline
fun downloadAllPaced(activityIds: List<Activity.ActivityId>): Multi<FitSource> {
    return Multi.createFrom().iterable(activityIds)
        // Clean fluent chaining! Upstream is automatically passed to spaceOut
        .stage(spaceOut(Duration.ofMillis(1500))) 
        .onItem().transformToUniAndConcat { id ->
            activitiesService.getOriginalSource(id)
        }
}

```

---

### How `.stage` Alters the Flow

Normally, custom operations require you to wrap your stream inside a nested method block, which breaks the visual readability of your code. `.stage` acts as an adapter that passes the current state of the stream into your custom logic without breaking the fluent pipeline design.

```text
[Original Multi Source]
         │
         ▼
  .stage(spaceOut)  ──► [ Takes Upstream Multi ]
                             │
                             ├──► Zips with Ticks Stream
                             │
         ┌───────────────────┘
         ▼
[ Paced Downstream Multi ]
         │
         ▼
.transformToUniAndConcat(...)

```

### Why this approach shines:

* **Separation of Concerns:** Your download logic doesn't need to know *how* the time delay is implemented. It just knows the items are arriving at a controlled pace.
* **RxJS-like Readability:** It keeps your code flowing forward without making you jump out to a messy `Multi.createBy().zipping(...)` structure right in the middle of your business logic.
* **Reusability:** You can now throw that `spaceOut` function into a utility file and instantly throttle *any* `Multi` in your codebase by just tacking `.stage(spaceOut(...))` onto it.