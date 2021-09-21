package org.common

interface IPipeline extends Serializable {

    def Setup()

    def Source()

    def Build()

    def PostBuild()

    def Run()

}

