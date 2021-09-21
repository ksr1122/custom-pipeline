import org.common.BasePipeline

def call() {
    node {
        def basePipeline = new BasePipeline(this)
        basePipeline.Run()
    }
}

